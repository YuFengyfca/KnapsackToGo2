package cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.command;

import cn.jji8.KnapsackToGo2.FieldMame;
import cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.Interactive;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
/**
 * 将命令发送给下游服务器后交给下游服务器处理
 * */
public class SetHome extends Command {
    public SetHome() {
        super("SetHome");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof ProxiedPlayer)){
            commandSender.sendMessage(new TextComponent("这个命令必须由玩家执行"));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        String HomeName;
        if(strings.length<1){
            HomeName = FieldMame.Player_Default_Home_Name;
        }else {
            HomeName = strings[0];
        }
        if(HomeName.contains(":")|HomeName.contains(";")|HomeName.contains(",")){
            commandSender.sendMessage(new TextComponent("家名称中不可用包含“:“、”;“、”,“符号。"));
            return;
        }
        Interactive.SeverCommend(player,FieldMame.Sub_Set_Home_command,new String[]{player.getServer().getInfo().getName(),HomeName});
    }
}
