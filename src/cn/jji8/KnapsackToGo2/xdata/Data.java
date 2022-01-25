package cn.jji8.KnapsackToGo2.xdata;
/**
 * 一个全局数据管理类
 * */
public class Data {
    public static final String FileAdta = "FileAdta1.0.jar";
    public static final String MysqData = "MysqData1.8.jar";
    static DataExample dataExample = null;
    /**
     * 获取数据管理器
     * */
    public static DataExample getDataExample() {
        return dataExample;
    }
    /**
     * 加载数据管理器
     * */
    public static void setDataExample(DataExample dataExample){
        Data.dataExample = dataExample;
    }
    /**
     * 获取某玩家的某个数据，建议使用"插件名_数据名"的方式命名数据。
     * */
    public static String get(String playerName,String key){
        if(dataExample==null){
            XLogger.severe("插件数据管理器没有加载，无法完玩家："+playerName+"的"+key+"的数据的查询操作！");
            return null;
        }
        return dataExample.get(playerName,key);
    }
    /**
     * 设置某玩家的某个数据，建议使用"插件名_数据名"的方式命名数据。
     * */
    public static void set(String playerName,String key,String value){
        if(dataExample==null){
            XLogger.severe("插件数据管理器没有加载，无法完玩家："+playerName+"的"+key+"的数据的设置操作！");
            return;
        }
        dataExample.set(playerName,key,value);
    }
    /**
     * 获取某插件的某个数据
     * */
    public static String getPluginData(String PluginName,String DataName){
        if(dataExample==null){
            XLogger.severe("插件数据管理器没有加载，无法完插件："+PluginName+"的"+DataName+"的数据的查询操作！");
            return null;
        }
        return dataExample.getPluginData(PluginName,DataName);
    }
    /**
     * 设置某插件的某个数据
     * */
    public static void setPluginData(String PluginName,String DataName,String value){
        if(dataExample==null){
            XLogger.severe("插件数据管理器没有加载，无法完插件："+PluginName+"的"+DataName+"的数据的设置操作！");
            return;
        }
        dataExample.setPluginData(PluginName,DataName,value);
    }
}
