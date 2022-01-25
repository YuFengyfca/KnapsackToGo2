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
 * home命令
 * 将玩家传送到家的服务器后，交给下游服务器处理
 * */
public class Home extends Command{
    public Home() {
        super("Home");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof ProxiedPlayer)){
            commandSender.sendMessage(new TextComponent("这个命令必须由玩家执行"));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        String HomeDataString = BungeeCordData.get(commandSender.getName(), FieldMame.Home_Data_Name);
        String homeName;
        if(strings.length<1){
            homeName = FieldMame.Player_Default_Home_Name;
        }else {
            homeName = strings[0];
        }
        HomeAllData homeAllData = new HomeAllData(HomeDataString);
        HomeAllData.HomeData homeData = homeAllData.getHomeData(homeName);
        if(homeData==null){
            commandSender.sendMessage(new TextComponent("你还没有设置家:"+homeName));
            StringBuilder in = new StringBuilder("你目前设置的家列表：");
            for (String s : homeAllData.homeDataMap.keySet()) {
                in.append(s);
                in.append(",");
            }
            commandSender.sendMessage(new TextComponent(in.toString()));
            return;
        }
        ServerInfo serverInfo = KTG2_experience.ktg2_experience.getProxy().getServerInfo(homeData.serverName);
        if(serverInfo==null){
            commandSender.sendMessage(new TextComponent("无法找到你的家"+homeData.serverName+"！"));
            return;
        }
        Interactive.TpSeverCommend(player,serverInfo,FieldMame.Sub_Go_Home_command,new String[]{homeName});
    }
}
