package cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord;


import cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.command.Home;
import cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.command.SetHome;
import cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.command.TpaAndTpaccept;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class KTG2_experience extends Plugin implements Listener {
    public static KTG2_experience ktg2_experience;

    @Override
    public void onLoad() {
        ktg2_experience = this;
    }

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this,new Interactive());
        getProxy().getPluginManager().registerCommand(this,new Home());
        getProxy().getPluginManager().registerCommand(this,new SetHome());
        getProxy().getPluginManager().registerCommand(this,new TpaAndTpaccept.Tpa());
        getProxy().getPluginManager().registerCommand(this,new TpaAndTpaccept.Tpaccept());
        getLogger().info("加载完成！");
    }
}
