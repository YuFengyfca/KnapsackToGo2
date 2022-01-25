package cn.jji8.KnapsackToGo2.spigot;


import cn.jji8.KnapsackToGo2.spigot.io.*;
import cn.jji8.KnapsackToGo2.spigot.synchronization.SynchronizationManager;
import cn.jji8.KnapsackToGo2.spigot.synchronization.Tips;
import cn.jji8.KnapsackToGo2.spigot.xdata.SpigotData;
import cn.jji8.KnapsackToGo2.xdata.DataLoad;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collection;

public class KnapsackToGo2Spigot extends JavaPlugin {
    public static KnapsackToGo2Spigot KnapsackToGo2Spigot;
    public KnapsackToGo2Spigot(){KnapsackToGo2Spigot = this;};
    @Override
    public void onLoad() {
        getLogger().info("开始初始化");
        { //释放配置文件
            File file = new File(getDataFolder(),"DataManager.yml");
            if(!file.exists()){
                ReleaseFile.release(file,"DataManager.yml",getLogger());
            }
        }
        { //释放配置文件
            File file = new File(getDataFolder(),"SynchronizationConfig.yml");
            if(!file.exists()){
                ReleaseFile.release(file,"SynchronizationConfig.yml",getLogger());
            }
        }
        { //释放并加载加载提示语配置文件
            File file = new File(getDataFolder(),"LoadingRandomMessages.yml");
            if(!file.exists()){
                ReleaseFile.release(file,"LoadingRandomMessages.yml",getLogger());
            }
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            Tips.tipsList = yamlConfiguration.getStringList("消息列表");
        }
        {//释放DataManager包
            File file = new File(getDataFolder(),"DataManager");
            if(!file.exists()){
                ReleaseFile.release(new File(file,SpigotData.FileAdta),"AdtaManager/"+SpigotData.FileAdta,getLogger());
                ReleaseFile.release(new File(file,SpigotData.MysqData),"AdtaManager/"+SpigotData.MysqData,getLogger());
            }
        }
        getLogger().info("初始化完成，准备加载！");
    }
    @Override
    public void onEnable() {
        //为插件收集数据
        new cn.jji8.KnapsackToGo2.spigot.bStats.Metrics(this,8687);
        getLogger().info("开始加载数据管理器！");
        String string = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"DataManager.yml")).getString("数据管理器");
        if (string == null) {
            getLogger().severe("没有设置数据管理器，请设置数据管理器，插件已禁用。");
            return;
        }
        try {
            DataLoad.load(new File(getDataFolder(),"DataManager/"+string),getDataFolder(),getLogger());
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().severe(string+"数据管理器无法加载，插件已禁用。");
            return;
        }
        getLogger().info("数据管理器加载完成！");
        getLogger().info("开始加同步执行器。");
        {//加载Synchronization
            YamlConfiguration SynchronizationConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"SynchronizationConfig.yml"));
            int waitingTime = SynchronizationConfig.getInt("等待时间");
            if(waitingTime<10){
                waitingTime = 10;
            }
            SynchronizationManager.waitingTime = waitingTime;

            int preservationTime = SynchronizationConfig.getInt("自动保存间隔");
            if(preservationTime<1){
                preservationTime = 1;
            }
            SynchronizationManager synchronizationManager = new SynchronizationManager(this);
            getServer().getScheduler().runTaskTimerAsynchronously(this,synchronizationManager,preservationTime*20,preservationTime*20);//注册自动保存
            getServer().getPluginManager().registerEvents(synchronizationManager,this);//注册监听器
            //注册控制器
            //同步背包控制器
            if(SynchronizationConfig.getBoolean("同步背包")){
                BeiBao beibao = new BeiBao(this);
                SynchronizationManager.addSynchronization(beibao);
                getLogger().info("同步背包开启");
            }
            if(SynchronizationConfig.getBoolean("同步末影箱")){
                MoYingXiang moyingxiang  = new MoYingXiang(this);
                SynchronizationManager.addSynchronization(moyingxiang );
                getLogger().info("同步末影箱开启");
            }
            if(SynchronizationConfig.getBoolean("同步血量饱食度")){
                XueLiangBaoShiDu xueliangbaoshidu = new XueLiangBaoShiDu(this);
                SynchronizationManager.addSynchronization(xueliangbaoshidu);
                getLogger().info("同步血量饱食度开启");
            }
            if(SynchronizationConfig.getBoolean("同步药水效果")){
                YaoSui yaosui = new YaoSui(this);
                SynchronizationManager.addSynchronization(yaosui);
                getLogger().info("同步药水效果开启");
            }
            if(SynchronizationConfig.getBoolean("同步经验")){
                JingYan jingyan = new JingYan(this);
                SynchronizationManager.addSynchronization(jingyan);
                getLogger().info("同步经验开启");
            }
            if(SynchronizationConfig.getBoolean("同步成就")){
                ChengJiu chengjiu = new ChengJiu(this);
                SynchronizationManager.addSynchronization(chengjiu);
                getLogger().info("同步成就开启");
            }

        }
        getLogger().info("同步执行器加载完成。");
    }

    @Override
    public void onDisable() {
       //保存全部玩家的数据，并解锁
        Collection collection = getServer().getOnlinePlayers();
        for(Object player:collection){
            if(player instanceof Player){
                if(SynchronizationManager.complete.containsKey(player)){
                    continue;
                }
                SynchronizationManager.saveData((Player) player);
                SynchronizationManager.unlocking((Player) player);
            }
        }
    }
}
