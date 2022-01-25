package cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.command;

import cn.jji8.KnapsackToGo2.FieldMame;
import cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.Interactive;
import cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.KTG2_experience;
import cn.jji8.KnapsackToGo2.bungeeCord.xData.BungeeCordData;
import cn.jji8.KnapsackToGo2.handle.HomeAllData;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
/**
 * 将指令发送到下游服务器处理
 * */
public class Spawn  extends Command {
    public Spawn() {
        super("Spawn");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof ProxiedPlayer)){
            commandSender.sendMessage(new TextComponent("这个命令必须由玩家执行"));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        String spawnStringData = BungeeCordData.getPluginData("KTG2_experience","Spawn");
        HomeAllData.HomeData spawn;
        try {
            assert spawnStringData != null;
            spawn = new HomeAllData.HomeData(spawnStringData);
        } catch (Exception e) {
            commandSender.sendMessage(new TextComponent("服务器没有设置主城！"));
            return;
        }
        ServerInfo serverInfo = KTG2_experience.ktg2_experience.getProxy().getServerInfo(spawn.serverName);
        if (serverInfo==null){
            commandSender.sendMessage(new TextComponent("主城服务器可能已失效！"));
            return;
        }
        Interactive.TpSeverCommend(player,serverInfo, FieldMame.Sub_Spawn_Command,new String[]{});
    }
}
