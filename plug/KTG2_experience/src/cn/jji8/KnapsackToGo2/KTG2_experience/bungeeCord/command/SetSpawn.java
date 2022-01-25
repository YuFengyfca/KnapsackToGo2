package cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.command;

import cn.jji8.KnapsackToGo2.FieldMame;
import cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord.Interactive;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
/**
 * 将指令发送到下游服务器处理
 * */
public class SetSpawn extends Command {
    public SetSpawn() {
        super("SetSpawn");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof ProxiedPlayer)){
            commandSender.sendMessage(new TextComponent("这个命令必须由玩家执行"));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        Interactive.SeverCommend(player, FieldMame.Sub_SetSpawn_Command,new String[]{player.getServer().getInfo().getName()});
    }
}
