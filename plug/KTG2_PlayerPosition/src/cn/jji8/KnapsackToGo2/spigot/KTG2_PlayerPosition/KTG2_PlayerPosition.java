package cn.jji8.KnapsackToGo2.spigot.KTG2_PlayerPosition;

import cn.jji8.KnapsackToGo2.spigot.synchronization.Synchronization;
import cn.jji8.KnapsackToGo2.spigot.synchronization.SynchronizationManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class KTG2_PlayerPosition extends JavaPlugin {
    KTG2_PlayerPosition KTG2_PlayerPosition = this;
    @Override
    public void onLoad() {
        SynchronizationManager.addSynchronization(new Synchronization() {
            @Override
            protected String getName() {
                return "KTG2_位置";
            }

            @Override
            protected void loadData(Player player, String Data) {
                YamlConfiguration yamlConfiguration = new YamlConfiguration();
                try {
                    yamlConfiguration.loadFromString(Data);
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                    getLogger().warning("加载玩家位置是发生错误。");
                }
                String word = yamlConfiguration.getString("World");
                if (word==null){
                    return;
                }
                World world = Bukkit.getWorld(word);
                if(world==null){
                    getLogger().warning(player.getName()+"的位置信息中，没有找到"+word+"世界！取消本次位置同步。");
                    return;
                }
                Location location = new Location(world,
                        yamlConfiguration.getDouble("X"),
                        yamlConfiguration.getDouble("Y"),
                        yamlConfiguration.getDouble("Z"),
                        (float) yamlConfiguration.getDouble("Yaw"),
                        (float) yamlConfiguration.getDouble("Pitch")
                );
                Bukkit.getServer().getScheduler().runTask(KTG2_PlayerPosition,()->player.teleport(location));
            }

            @Override
            protected String saveData(Player player) {
                Location location = player.getLocation();
                YamlConfiguration yamlConfiguration = new YamlConfiguration();
                yamlConfiguration.set("World",location.getWorld().getName());
                yamlConfiguration.set("X",location.getX());
                yamlConfiguration.set("Y",location.getY());
                yamlConfiguration.set("Z",location.getZ());
                yamlConfiguration.set("Yaw",(double)location.getYaw());
                yamlConfiguration.set("Pitch",(double)location.getPitch());
                return yamlConfiguration.saveToString();
            }
        });
        getLogger().info("位置同步加载完成");
    }
}
