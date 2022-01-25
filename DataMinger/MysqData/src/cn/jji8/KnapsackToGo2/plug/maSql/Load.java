package cn.jji8.KnapsackToGo2.plug.maSql;

import cn.jji8.KnapsackToGo2.xdata.DataExample;
import cn.jji8.KnapsackToGo2.xdata.DataExampleLoad;
import cn.jji8.KnapsackToGo2.xdata.DependencyLoader;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 使用了MysqlSimpleUse 开源地址https://gitee.com/jji8/MysqlSimpleUse
 * */
public class Load implements DataExampleLoad {

    public static final String MysqlSimpleUse = "MysqlSimpleUse1.18.jar";
    static {
        Path cache = Paths.get("mysql数据库驱动");
        File file = new File(cache.toFile(),MysqlSimpleUse);
        if(!file.exists()){
            try {
                System.out.println("释放依赖库到:"+file.getAbsoluteFile());
                URL url1 = DataMysqlExample.class.getClassLoader().getResource(MysqlSimpleUse);
                assert url1 != null;
                InputStream inputStream = url1.openStream();
                cache.toFile().mkdirs();
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
                System.out.println("无法加载MysqlSimpleUse数据库依赖！");
            }
        }
        DependencyLoader.addToClassPath(file.toPath());
    }

    public DataExample load(File ConfigurationFolder, Logger logger) {
        File file = new File(ConfigurationFolder, "MysqSynchronization.properties");
        if(!file.exists()){
            try {
                logger.info("释放MysqSynchronization.properties到:"+file.getAbsoluteFile());
                URL url1 = Load.class.getClassLoader().getResource("MysqSynchronization.properties");
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
                logger.warning("无法释放MysqSynchronization.properties");
            }
        }
        try {
            FileReader fileReader = new FileReader(file);
            Properties properties = new Properties();
            properties.load(fileReader);
            do{
                try {
                    String address = properties.getProperty("address");
                    String username = properties.getProperty("username");
                    String password = properties.getProperty("password");
                    String TableName = properties.getProperty("TableName");
                    logger.info("----------------------------");
                    logger.info("下面是连接的数据库信息：");
                    logger.info("数据库地址是："+address);
                    logger.info("用户名是："+username);
                    logger.info("使用的表是："+TableName);
                    logger.info("----------------------------");
                    return new DataMysqlExample(address,username,password,TableName);
                } catch (SQLException| RuntimeException throwables) {
                    throwables.printStackTrace();
                    logger.warning("数据库连接失败，数据管理器未加载，一秒后尝试重新连接。");
                    try { Thread.sleep(1000); } catch (InterruptedException ignored) { }
                }
            }while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
