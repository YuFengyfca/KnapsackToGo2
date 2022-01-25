package cn.jji8.KnapsackToGo2.spigot.synchronization;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tips {
    //加载时的提示列表,和当前使用的提示。
    public static List<String> tipsList = new ArrayList<>();
    /**
     * 从提示列表中随机取出一条提示，如果提示列表中没有数据，则返回§d正在加载你的数据，请稍等.
     * */
    public static String getRandomTips(){
        if(tipsList==null){
            return "§d正在加载你的数据，请稍等.";
        }
        if(tipsList.size()<1){
            return "§d正在加载你的数据，请稍等.";
        }
        Random random = new Random();
        int n = random.nextInt(tipsList.size());
        String s = tipsList.get(n);
        if(s==null){
            return "§d正在加载你的数据，请稍等.";
        }
        return s;
    }
    /**
     * 给玩家反馈加载消息
     * */
    static void sendTitle(Player player, String x){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(x));
    }

    public static TipsMainger showTips(Plugin plugin, Player player, String news){
        TipsMainger tipsMainger = new TipsMainger(news);
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            String RandomTips = getRandomTips();
            int i = 0;
            @Override
            public void run() {
                i++;
                String x = "o";
                switch (i%4){
                    case 0: x="|";break;
                    case 1: x="/";break;
                    case 2: x="-";break;
                    case 3: x="\\";break;
                }
                sendTitle(player,RandomTips+tipsMainger.news+".§e"+x);
                if(tipsMainger.isCancel()){
                    sendTitle(player,"完成！");
                    return;
                }
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin,this,2);
            }
        });
        return tipsMainger;
    }

    public static class TipsMainger{
        //提示消息
        String news;
        public TipsMainger(String news) {
            this.news = news;
        }
        /**获取提示消息*/
        public String getNews() {
            return news;
        }
        /**设置提示消息*/
        public void setNews(String news) {
            this.news = news;
        }
        /**取消*/
        boolean cancel = false;
        /**设置取消*/
        void cancel(boolean b){
            cancel =b;
        }
        /**是取消的*/
        boolean isCancel(){
            return cancel;
        }
    }
}
