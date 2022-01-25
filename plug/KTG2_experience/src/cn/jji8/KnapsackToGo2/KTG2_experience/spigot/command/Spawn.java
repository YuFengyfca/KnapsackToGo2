package cn.jji8.KnapsackToGo2.KTG2_experience.spigot.command;

import cn.jji8.KnapsackToGo2.FieldMame;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.Interactive;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.KTG2_experience;
import cn.jji8.KnapsackToGo2.handle.HomeAllData;
import cn.jji8.KnapsackToGo2.spigot.xdata.SpigotData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Spawn implements Interactive.CommandReceiver{
    /**
     * 用于接命令的前缀
     */
    @Override
    public String getCommandName() {
        return FieldMame.Sub_Spawn_Command;
    }

    /**
     * 接到的数据
     */
    @Override
    public void CommandData(Player player, String[] data) {
        //异步查询数据
        KTG2_experience.ktg2_experience.getServer().getScheduler().runTaskAsynchronously(KTG2_experience.ktg2_experience, () ->{
            String spawnStringData = SpigotData.getPluginData("KTG2_experience","Spawn");
            HomeAllData.HomeData spawn;
            try {
                assert spawnStringData != null;
                spawn = new HomeAllData.HomeData(spawnStringData);
            } catch (Exception e) {
                player.sendMessage("服务器没有设置主城！");
                return;
            }
            World world = Bukkit.getWorld(spawn.WordMame);
            if(world==null){
                player.sendMessage("主城可能已经失效！");
            }
            //同步传送玩家
            KTG2_experience.ktg2_experience.getServer().getScheduler().runTask(KTG2_experience.ktg2_experience,()->player.teleport(new Location(world,spawn.X,spawn.Y,spawn.Z,(float) spawn.P,(float) spawn.Q)));
        });
    }
}
