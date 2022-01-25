package cn.jji8.KnapsackToGo2.KTG2_experience.bungeeCord;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用于处理bungeeCord与spigot之间的交互
 * */
public class Interactive implements Listener {
    static class PlayerData{
        ServerInfo serverInfo;
        String command;
        long time;
        public PlayerData(ServerInfo serverInfo, String command) {
            this.serverInfo = serverInfo;
            this.command = command;
            this.time = System.currentTimeMillis();
        }
    }
    static Map<ProxiedPlayer,PlayerData> PlayerDataMap = new HashMap<>();
    /**
     * 监听玩家加入某个服务器
     * */
    @EventHandler
    public void ServerConnectedEvent(ServerConnectedEvent e){
        KTG2_experience.ktg2_experience.getProxy().getScheduler().schedule(KTG2_experience.ktg2_experience, () -> {
            PlayerData playerData = PlayerDataMap.get(e.getPlayer());
            if(playerData==null){
                return;
            }
            if(!e.getServer().getInfo().equals(playerData.serverInfo)){
                return;
            }
            if(playerData.time+1000*10<System.currentTimeMillis()){
                return;
            }
            PlayerDataMap.remove(e.getPlayer());
            e.getPlayer().chat(playerData.command);
        },1,TimeUnit.SECONDS);
    }
    /**
     * 将玩家传送到指定服务器并在合适的时间将命令已玩家的身份发送给服务器
     * */
    public static void TpSeverCommend(ProxiedPlayer player, ServerInfo serverInfo, String command,String[] data){
        StringBuilder stringBuffer = new StringBuilder(command);
        for (String datum : data) {
            stringBuffer.append(datum);
            stringBuffer.append(":");
        }
        stringBuffer.append(" ");
        if(player.getServer().getInfo().equals(serverInfo)){
            player.chat(stringBuffer.toString());
            return;
        }
        PlayerDataMap.put(player,new PlayerData(serverInfo,stringBuffer.toString()));
        player.connect(serverInfo);
    }
    /**
     * 让玩家向他所在的子服务器发送命令
     * */
    public static void SeverCommend(ProxiedPlayer player, String command,String[] data){
        StringBuilder stringBuffer = new StringBuilder(command);
        for (String datum : data) {
            stringBuffer.append(datum);
            stringBuffer.append(":");
        }
        stringBuffer.append(" ");
        player.chat(stringBuffer.toString());
    }
}
