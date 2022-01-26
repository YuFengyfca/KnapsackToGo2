package cn.jji8.KnapsackToGo2.spigot.PlayerEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * 数据加载完成事件
 * */
public class DataLoadComplete extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    public DataLoadComplete(Player who) {
        super(who);
    }
    public HandlerList getHandlers() {
        return handlers;
    }
}
