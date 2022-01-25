package cn.jji8.KnapsackToGo2.KTG2_experience.spigot;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于处理bungeeCord与spigot之间的交互
 * */
public class Interactive implements Listener {
    public interface CommandReceiver{
        /**
         * 用于接命令的前缀
         * */
        String getCommandName();
        /**
         * 接到的数据
         * */
        void CommandData(Player player, String[] data);
    }
    static List<CommandReceiver> receiverList = new ArrayList<>();
    /**
     * 添加一个命令接收器
     * */
    public static void addReceiver(CommandReceiver commandReceiver){
        receiverList.add(commandReceiver);
    }
    /**
     * 处理玩家的消息和命令
     * */
    boolean handle(Player player,String command){
        for (CommandReceiver commandReceiver : receiverList) {
            if(command.startsWith(commandReceiver.getCommandName())){
                commandReceiver.CommandData(player,command.substring(commandReceiver.getCommandName().length()).split(":"));
                return true;
            }
        }
        return false;
    }
    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent a){//监听玩家发送消息
        if(handle(a.getPlayer(),a.getMessage())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent a){//监听玩家发送命令
        if(handle(a.getPlayer(),a.getMessage())){
            a.setCancelled(true);
        }
    }
}
