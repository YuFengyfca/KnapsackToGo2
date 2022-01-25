package cn.jji8.KnapsackToGo2.spigot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

public class ReleaseFile {
    public static void release(File file, String route, Logger logger){
        try {
            logger.info("释放文件到:" + file.getAbsoluteFile());
            URL url1 = ReleaseFile.class.getClassLoader().getResource(route);
            InputStream inputStream = url1.openStream();
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
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
            logger.warning("无法将释放文件到"+file.getAbsoluteFile());
        }
    }
}
