package cn.jji8.KnapsackToGo2.plug.maSql;

import cn.jji8.KnapsackToGo2.xdata.DataExample;

import cn.jji8.mysqlUse.mySqlMap.SqlStringStringMap;
import cn.jji8.mysqlUse.mySqlSimpleUse.SqlConnect;
import cn.jji8.mysqlUse.mySqlSimpleUse.SqlTable;
import cn.jji8.mysqlUse.mySqlSimpleUse.column.Conlumn;
import cn.jji8.mysqlUse.mySqlSimpleUse.column.TextConlumn;
import cn.jji8.mysqlUse.mySqlSimpleUse.queryCondition.QueryCondition;
import cn.jji8.mysqlUse.mySqlSimpleUse.queryResults.QueryResults;

import java.sql.SQLException;
import java.util.*;

/**
 * 使用了MysqlSimpleUse 开源地址https://gitee.com/jji8/MysqlSimpleUse
 * */
public class DataMysqlExample implements DataExample {
    //数据库的连接
    SqlConnect sqlConnect;
    //表名
    String tableName;
    //数据库map的map
    Map<String,SqlStringStringMap> stringSqlStringStringMapMap = new HashMap<>();
    //插件数据表格
    SqlTable pluginTadle;
    public DataMysqlExample(String url,String user,String password,String tableName) throws SQLException {
        sqlConnect = new SqlConnect(url,user,password);
        this.tableName = tableName;

        HashSet<Conlumn> conlumnSet = new HashSet<>();
        conlumnSet.add(new TextConlumn("plugin"));
        conlumnSet.add(new TextConlumn("dataName"));
        conlumnSet.add(new TextConlumn("Data"));
        pluginTadle = sqlConnect.getTable(tableName + "_Pdata", conlumnSet);
    }
    /**
     * 获取某玩家的某个数据
     */
    @Override
    public String get(String playerName, String key) {
        SqlStringStringMap sqlStringStringMap = stringSqlStringStringMapMap.get(key);
        if(sqlStringStringMap==null){
            stringSqlStringStringMapMap.put(key,sqlStringStringMap = new SqlStringStringMap(sqlConnect,tableName,"player",key));
        }
        return sqlStringStringMap.get(playerName);
    }

    /**
     * 设置某玩家的某个数据
     */
    @Override
    public void set(String playerName, String key, String value) {
        SqlStringStringMap sqlStringStringMap = stringSqlStringStringMapMap.get(key);
        if(sqlStringStringMap==null){
            stringSqlStringStringMapMap.put(key,sqlStringStringMap = new SqlStringStringMap(sqlConnect,tableName,"player",key));
        }
        sqlStringStringMap.put(playerName,value);
    }

    /**
     * 获取某插件的某个数据
     */
    @Override
    public String getPluginData(String pluginName, String dataName) {
        Set<QueryCondition> queryConditionSet = new HashSet<>();
        queryConditionSet.add(new TextConlumn("plugin").getEqualCondition(pluginName));
        queryConditionSet.add(new TextConlumn("dataName").getEqualCondition(dataName));
        QueryResults queryResults = pluginTadle.query(new TextConlumn("Data"), queryConditionSet);
        Map<Integer, String> dataMap = queryResults.getResults(new TextConlumn("Data"));
        Collection<String> dataMapValues = dataMap.values();
        String data = null;
        for (String dataMapValue : dataMapValues) {
            data = dataMapValue;
        }
        return data;
    }

    /**
     * 设置某插件的某个数据
     */
    @Override
    public void setPluginData(String pluginName, String dataName, String value) {
        Set<QueryCondition> queryConditionSet = new HashSet<>();
        queryConditionSet.add(new TextConlumn("plugin").getEqualCondition(pluginName));
        queryConditionSet.add(new TextConlumn("dataName").getEqualCondition(dataName));
        QueryResults queryResults = pluginTadle.query(new TextConlumn("Data"), queryConditionSet);
        Map<Integer, String> dataMap = queryResults.getResults(new TextConlumn("Data"));
        for (Integer integer : dataMap.keySet()) {
            pluginTadle.delete(integer);
        }
        HashMap<Conlumn,Object> hashMap = new HashMap();
        hashMap.put(new TextConlumn("plugin"),pluginName);
        hashMap.put(new TextConlumn("dataName"),dataName);
        pluginTadle.add(hashMap);
    }
}
