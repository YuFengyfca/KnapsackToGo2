package cn.jji8.KnapsackToGo2.plug.file;

import cn.jji8.KnapsackToGo2.abnormal.AbnormalFileReading;
import cn.jji8.KnapsackToGo2.abnormal.FileWriteException;
import cn.jji8.KnapsackToGo2.xdata.DataExample;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataFileExample implements DataExample {
    File file;
    public DataFileExample(File file) {
        this.file = file;
    }
    /**
     * 获取某玩家的某个数据
     */
    @Override
    public String get(String playerName, String key) {
        File data = new File(new File(file,key),playerName+".yml");
        try {
            new File(data,"/..").mkdirs();
            data.createNewFile();
            FileReader fileReader = new FileReader(data);
            StringBuilder stringBuilder = new StringBuilder();
            while (true){
                int i = fileReader.read();
                if(i==-1){
                    break;
                }
                stringBuilder.append((char)i);
            }
            fileReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new AbnormalFileReading("读取文件"+data.toString()+"时出错,请检查文件权限",e);
        }
    }

    /**
     * 设置某玩家的某个数据
     */
    @Override
    public void set(String playerName, String key, String value) {
        File data = new File(new File(file,key),playerName+".yml");
        try {
            new File(data,"/..").mkdirs();
            data.createNewFile();
            FileWriter fileWriter = new FileWriter(data);
            fileWriter.append(value);
            fileWriter.close();
        } catch (IOException e) {
            throw new FileWriteException("在保存"+data+"文件时发生错误,请检查文件权限",e);
        }
    }

    /**
     * 获取某插件的某个数据
     */
    @Override
    public String getPluginData(String pluginName, String dataName) {
        File data = new File(new File(new File(file,"plugin.Data"),pluginName),dataName+".yml");
        try {
            new File(data,"/..").mkdirs();
            data.createNewFile();
            FileReader fileReader = new FileReader(data);
            StringBuilder stringBuilder = new StringBuilder();
            while (true){
                int i = fileReader.read();
                if(i==-1){
                    break;
                }
                stringBuilder.append((char)i);
            }
            fileReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new AbnormalFileReading("读取文件"+data.toString()+"时出错,请检查文件权限",e);
        }
    }

    /**
     * 设置某插件的某个数据
     */
    @Override
    public void setPluginData(String pluginName, String dataName, String value) {
        File data = new File(new File(new File(file,"plugin.Data"),pluginName),dataName+".yml");
        try {
            new File(data,"/..").mkdirs();
            data.createNewFile();
            FileWriter fileWriter = new FileWriter(data);
            fileWriter.append(value);
            fileWriter.close();
        } catch (IOException e) {
            throw new FileWriteException("在保存"+data+"文件时发生错误,请检查文件权限",e);
        }
    }
}
