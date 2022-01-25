package cn.jji8.KnapsackToGo2.xdata;

import java.io.File;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;
/**
 * 用于加载数据管理器
 * */
public class DataLoad {
    static {
        DependencyLoader.addToClassPath(DataLoad.class.getProtectionDomain().getCodeSource().getLocation());
    }
    /**
     * 加载指定位置的数据管理器
     * */
    public static void load(File jarFile, File ConfigurationFolder,Logger logger) throws Exception {
        JarFile jar = new JarFile(jarFile);
        JarEntry jarEntry = jar.getJarEntry("main.load");
        InputStream inputStream = jar.getInputStream(jarEntry);
        StringBuilder stringBuilder = new StringBuilder();
        while (true){
           int i = inputStream.read();
           if(i==-1){
               break;
           }
            stringBuilder.append((char)i);
        }
        inputStream.close();
        DependencyLoader.addToClassPath(jarFile.toPath());
        Class c = Class.forName(stringBuilder.toString());
        Object obj = c.newInstance();
        if(obj instanceof DataExampleLoad){
            DataExample dataExample =((DataExampleLoad)obj).load(ConfigurationFolder,logger);
            if(dataExample==null){
                throw new Exception(stringBuilder.toString()+"没有返回一个DataExample");
            }
            Data.setDataExample(dataExample);
        }else {
            throw new Exception(stringBuilder.toString()+"不是一个DataExampleLoad");
        }
    }
}
