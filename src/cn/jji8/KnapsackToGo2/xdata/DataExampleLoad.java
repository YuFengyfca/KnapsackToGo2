package cn.jji8.KnapsackToGo2.xdata;

import java.io.File;
import java.util.logging.Logger;
/**
 * 用于加载负责管理数据的类必须实现的类
 * */
public interface DataExampleLoad {
    DataExample load(File ConfigurationFolder, Logger logger);
}
