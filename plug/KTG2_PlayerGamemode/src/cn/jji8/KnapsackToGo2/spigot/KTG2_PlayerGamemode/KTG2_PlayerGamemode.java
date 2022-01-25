package cn.jji8.KnapsackToGo2.spigot.KTG2_PlayerGamemode;

import cn.jji8.KnapsackToGo2.spigot.synchronization.Synchronization;
import cn.jji8.KnapsackToGo2.spigot.synchronization.SynchronizationManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class KTG2_PlayerGamemode extends JavaPlugin {
    KTG2_PlayerGamemode KTG2_PlayerGamemode = this;
    @Override
    public void onLoad() {
        SynchronizationManager.addSynchronization(new Synchronization() {
            @Override
            protected String getName() {
                return "KTG2_游戏模式";
            }

            @Override
            protected void loadData(Player player, String Data) {
                GameMode gameMode;
                try {
                    gameMode = GameMode.valueOf(Data);
                }catch (IllegalArgumentException ignored){
                    return;
                }
                Bukkit.getServer().getScheduler().runTask(KTG2_PlayerGamemode, () -> player.setGameMode(gameMode));
            }

            @Override
            protected String saveData(Player player) {
                return player.getGameMode().toString();
            }
        });
        getLogger().info("游戏模式同步加载完成");
    }
}
