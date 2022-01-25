package cn.jji8.KnapsackToGo2.xdata;


import cn.jji8.KnapsackToGo2.bungeeCord.KnapsackToGo2Bc;
import cn.jji8.KnapsackToGo2.spigot.KnapsackToGo2Spigot;

import java.util.logging.Logger;

/**
 * 负责科学的输出日志
 * */
public class XLogger {
    static Logger logger = null;
    static boolean logger(){
        if(logger!=null){
            return true;
        }
        if(KnapsackToGo2Bc.KnapsackToGo2Bc!=null){
            logger = KnapsackToGo2Bc.KnapsackToGo2Bc.getLogger();
        }
        if(KnapsackToGo2Spigot.KnapsackToGo2Spigot!=null){
            logger = KnapsackToGo2Spigot.KnapsackToGo2Spigot.getLogger();
        }
        return logger!=null;
    }
    public static void info(String info){
        if(!logger()){
            System.out.println(info);
        }
        logger.info(info);
    }
    public static void warning(String info){
        if(!logger()){
            System.out.println(info);
        }
        logger.warning(info);
    }
    public static void severe(String info){
        if(!logger()){
            System.out.println(info);
        }
        logger.severe(info);
    }
}
