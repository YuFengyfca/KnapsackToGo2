package cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.command;

import cn.jji8.KnapsackToGo2.FieldMame;
import cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.Interactive;
import cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.KTG2_experience;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.Map;
/**
 * 处理完成请求后，将玩家连接到对应服务器后，交给下游服务器处理。
 * */
public class TpaAndTpaccept{
    static Map<ProxiedPlayer,ProxiedPlayer> PlayerTpaMap = new HashMap<>();
    public static class Tpaccept extends Command {
        public Tpaccept() {
            super("Tpaccept");
        }
        @Override
        public void execute(CommandSender commandSender, String[] strings) {
            if(!(commandSender instanceof ProxiedPlayer)){
                commandSender.sendMessage(new TextComponent("这个命令必须由玩家执行"));
                return;
            }
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            ProxiedPlayer tpPlayer = PlayerTpaMap.get(player);
            if(tpPlayer==null){
                commandSender.sendMessage(new TextComponent("你没有待处理的请求。"));
            }
            Interactive.TpSeverCommend(tpPlayer,player.getServer().getInfo(), FieldMame.Sub_Tpa_Command,new String[]{player.getName()});
            commandSender.sendMessage(new TextComponent("你已接受"+tpPlayer+"的请求。"));
            PlayerTpaMap.remove(player);
        }
    }
    public static class Tpa extends Command {
        public Tpa() {
            super("Tpa");
        }
        @Override
        public void execute(CommandSender commandSender, String[] strings) {
            if(!(commandSender instanceof ProxiedPlayer)){
                commandSender.sendMessage(new TextComponent("这个命令必须由玩家执行"));
                return;
            }
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            if(strings.length<1){
                commandSender.sendMessage(new TextComponent("/tpa <玩家名>"));
                return;
            }
            ProxiedPlayer beTpPlayer = KTG2_experience.ktg2_experience.getProxy().getPlayer(strings[0]);
            if(beTpPlayer==null){
                commandSender.sendMessage(new TextComponent("玩家"+strings[0]+"不在线"));
                return;
            }
            PlayerTpaMap.put(beTpPlayer,player);
            commandSender.sendMessage(new TextComponent("等待"+strings[0]+"接受你的请求。"));
            //告诉被TP的玩家有人TP他
            beTpPlayer.sendMessage(new TextComponent("TPA请求:"));
            beTpPlayer.sendMessage(new TextComponent(player.getName()+"请求传送到你这里。"));
            beTpPlayer.sendMessage(new TextComponent("在聊天栏输入“/tpaccept”接受他的请求。"));
        }
    }
}
