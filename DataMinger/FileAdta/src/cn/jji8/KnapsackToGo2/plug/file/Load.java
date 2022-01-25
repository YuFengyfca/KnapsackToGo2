package cn.jji8.KnapsackToGo2.plug.file;

import cn.jji8.KnapsackToGo2.xdata.DataExample;
import cn.jji8.KnapsackToGo2.xdata.DataExampleLoad;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public class Load implements DataExampleLoad {
    public DataExample load(File ConfigurationFolder, Logger logger) {
        File file = new File(ConfigurationFolder, "FileSynchronization.properties");
        if(!file.exists()){
            try {
                logger.info("释放FileSynchronization.properties到:"+file.getAbsoluteFile());
                URL url1 = Load.class.getClassLoader().getResource("FileSynchronization.properties");
                InputStream inputStream = url1.openStream();
                ConfigurationFolder.mkdirs();
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while (true){
                    int i = inputStream.read();
                    if(i==-1){
                        break;
                    }
                    fileOutputStream.write(i);
                }
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                logger.severe("无法释放FileSynchronization.properties");
            }
        }
        try {
            FileReader fileReader = new FileReader(file);
            Properties properties = new Properties();
            properties.load(fileReader);
            String s = properties.getProperty("PlayerDataSavingPath");
            if(s==null){
                logger.warning("FileSynchronization.properties中PlayerDataSavingPath值为空！");
                logger.warning("配置文件错误，加载取消！");
                return null;
            }
            File dataFile = new File(s);
            logger.info("----------------------------");
            logger.info("下面是同步文件夹信息：");
            logger.info("数据保存路径是:"+dataFile.getAbsolutePath());
            logger.info("----------------------------");
            return new DataFileExample(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning("无法加载FileSynchronization.properties配置文件");
        }
        return null;
    }
}
