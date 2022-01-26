package cn.jji8.KnapsackToGo2.bungeeCord;

import cn.jji8.KnapsackToGo2.bungeeCord.xData.BungeeCordData;
import cn.jji8.KnapsackToGo2.spigot.ReleaseFile;
import cn.jji8.KnapsackToGo2.xdata.DataLoad;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;


public class KnapsackToGo2Bc extends Plugin implements Listener {
    public static KnapsackToGo2Bc KnapsackToGo2Bc =null;
    public KnapsackToGo2Bc(){
        KnapsackToGo2Bc = this;
    }
    @Override
    public void onLoad() {
        getLogger().info("开始初始化");
        { //释放配置文件
            File file = new File(getDataFolder(),"DataManager.yml");
            if(!file.exists()){
                ReleaseFile.release(file,"DataManager.yml",getLogger());
            }

        }
        {//释放DataManager包
            File file = new File(getDataFolder(),"DataManager");
            if(!file.exists()){
                ReleaseFile.release(new File(file,BungeeCordData.FileAdta),"AdtaManager/"+BungeeCordData.FileAdta,getLogger());
                ReleaseFile.release(new File(file,BungeeCordData.MysqData),"AdtaManager/"+BungeeCordData.MysqData,getLogger());
            }
        }
        getLogger().info("初始化完成，准备加载！");
    }

    @Override
    public void onEnable() {
        getLogger().info("开始加载数据管理器！");
        String string = null;
        try {
            string = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(),"DataManager.yml")).getString("数据管理器");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        getProxy().getPluginManager().registerListener(this,this);
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {//玩家连接代理时
        //为玩家解锁
        BungeeCordData.set(event.getPlayer().getUniqueId().toString(),"lock","false");
    }
    @EventHandler
    public void onPostLogin(PlayerDisconnectEvent event) {//玩家离开代理时

    }
}
