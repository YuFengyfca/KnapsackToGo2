package cn.jji8.KnapsackToGo2.KTG2_experience.spigot.command;

import cn.jji8.KnapsackToGo2.FieldMame;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.Interactive;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.KTG2_experience;
import cn.jji8.KnapsackToGo2.handle.HomeAllData;
import cn.jji8.KnapsackToGo2.spigot.xdata.SpigotData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetSpawn implements Interactive.CommandReceiver{
    /**
     * 用于接命令的前缀
     */
    @Override
    public String getCommandName() {
        return FieldMame.Sub_SetSpawn_Command;
    }

    /**
     * 接到的数据
     *
     * @param player
     * @param data
     */
    @Override
    public void CommandData(Player player, String[] data) {
        if(!player.hasPermission("KTG2_experience.admin")){
            player.sendMessage("你没有权限使用本命令！需要：KTG2_experience.admin");
            return;
        }
        //异步设置数据
        KTG2_experience.ktg2_experience.getServer().getScheduler().runTaskAsynchronously(KTG2_experience.ktg2_experience, () ->{
            if(data.length<1){
                player.sendMessage("参数解析异常，请尝试重新执行命令！");
            }
            Location playerLocation = player.getLocation();
            String wordName = playerLocation.getWorld().getName();
            double x = playerLocation.getX();
            double y = playerLocation.getY();
            double z = playerLocation.getZ();
            double p = playerLocation.getYaw();
            double q = playerLocation.getPitch();
            HomeAllData.HomeData spawn = new HomeAllData.HomeData(data[0],wordName,x,y,z,p,q);
            SpigotData.setPluginData("KTG2_experience","Spawn",spawn.toString());
            player.sendMessage("设置成功！");
        });
    }
}
