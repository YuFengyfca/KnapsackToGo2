package cn.jji8.KnapsackToGo2.KTG2_experience.spigot.command;

import cn.jji8.KnapsackToGo2.FieldMame;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.Interactive;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TpaAndTpaccept implements Interactive.CommandReceiver{

    /**
     * 用于接命令的前缀
     */
    @Override
    public String getCommandName() {
        return FieldMame.Sub_Tpa_Command;
    }

    /**
     * 接到的数据
     */
    @Override
    public void CommandData(Player player, String[] data) {
        if(data.length<1){
            player.sendRawMessage("命令参数解析错误，请联系管理员。");
        }
        Player beTpPlayer = Bukkit.getPlayer(data[0]);
        if(beTpPlayer==null){
            player.sendRawMessage("你TP的玩家可能在你传送之前切换了其他服务器，请重试。");
            return;
        }
        player.teleport(beTpPlayer);
    }
}
