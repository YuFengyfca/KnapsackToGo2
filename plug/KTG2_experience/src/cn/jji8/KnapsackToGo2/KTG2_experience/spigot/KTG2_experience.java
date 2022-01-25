package cn.jji8.KnapsackToGo2.KTG2_experience.spigot;


import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.command.Home;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.command.SetHome;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.command.Spawn;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.command.TpaAndTpaccept;
import org.bukkit.plugin.java.JavaPlugin;

public class KTG2_experience extends JavaPlugin {
    public static KTG2_experience ktg2_experience;
    public KTG2_experience(){
        ktg2_experience = this;
    }
    @Override
    public void onLoad() {

    }
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Interactive(),this);//注册监听器
        Interactive.addReceiver(new Home());
        Interactive.addReceiver(new SetHome());
        Interactive.addReceiver(new TpaAndTpaccept());
        Interactive.addReceiver(new Spawn());
        getLogger().info("加载完成！");
    }
}
