package cn.jji8.KnapsackToGo2.xdata;
/**
 * 用于负责数据存储的类必须实现的类
 * */
public interface DataExample {
    /**
     * 获取某玩家的某个数据
     * */
    String get(String playerName,String key);
    /**
     * 设置某玩家的某个数据
     * */
    void set(String playerName,String key,String value);


    /**
     * 获取某插件的某个数据
     * */
    String getPluginData(String pluginName, String dataName);
    /**
     * 设置某插件的某个数据
     * */
    void setPluginData(String pluginName, String dataName, String value);
}
