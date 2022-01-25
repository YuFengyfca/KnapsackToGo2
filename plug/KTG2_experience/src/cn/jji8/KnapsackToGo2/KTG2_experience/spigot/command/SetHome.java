package cn.jji8.KnapsackToGo2.KTG2_experience.spigot.command;

import cn.jji8.KnapsackToGo2.FieldMame;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.Interactive;
import cn.jji8.KnapsackToGo2.KTG2_experience.spigot.KTG2_experience;
import cn.jji8.KnapsackToGo2.handle.HomeAllData;
import cn.jji8.KnapsackToGo2.spigot.xdata.SpigotData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetHome implements Interactive.CommandReceiver{
    /**
     * 用于接命令的前缀
     */
    @Override
    public String getCommandName() {
        return FieldMame.Sub_Set_Home_command;
    }

    /**
     * 接到的数据
     */
    @Override
    public void CommandData(Player player, String[] data) {
        //异步处理数据
        KTG2_experience.ktg2_experience.getServer().getScheduler().runTaskAsynchronously(KTG2_experience.ktg2_experience, () -> {
            if(data.length<2){
                player.sendRawMessage("解析SetHome参数时发送错误,请联系管理员！");
            }
            Location playerLocation = player.getLocation();
            String wordName = playerLocation.getWorld().getName();
            double x = playerLocation.getX();
            double y = playerLocation.getY();
            double z = playerLocation.getZ();
            double p = playerLocation.getYaw();
            double q = playerLocation.getPitch();
            String playerHomeAllStringData = SpigotData.get(player.getName(),FieldMame.Home_Data_Name);
            HomeAllData homeAllData = new HomeAllData(playerHomeAllStringData);
            homeAllData.putHome(data[1],new HomeAllData.HomeData(data[0],wordName,x,y,z,p,q));
            String newAllHomeStringData = homeAllData.toString();
            SpigotData.set(player.getName(),FieldMame.Home_Data_Name,newAllHomeStringData);
            player.sendRawMessage("设置成功。");
        });
    }
}
