package cn.jji8.KnapsackToGo2.spigot.io;

import cn.jji8.KnapsackToGo2.spigot.synchronization.Synchronization;
import org.bukkit.plugin.Plugin;

public abstract class Io extends Synchronization {
    Plugin plugin;
    Io(Plugin plugin){
        this.plugin = plugin;
    }
}
