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

public class Home implements Interactive.CommandReceiver {
    /**
     * 用于接命令的前缀
     */
    @Override
    public String getCommandName() {
        return FieldMame.Sub_Go_Home_command;
    }
    /**
     * 接到的数据
     */
    @Override
    public void CommandData(Player player,String[] data) {
        //在异步查询数据库
        KTG2_experience.ktg2_experience.getServer().getScheduler().runTaskAsynchronously(KTG2_experience.ktg2_experience, () -> {
            for (String datum : data) {
                System.out.println(datum);
            }
            String homeData = SpigotData.get(player.getUniqueId().toString(),FieldMame.Home_Data_Name);
            HomeAllData homeAllData = new HomeAllData(homeData);
            if(data.length!=1){
                return;
            }
            HomeAllData.HomeData homeData1 = homeAllData.getHomeData(data[0]);
            if(homeData1==null){
                player.sendRawMessage("没有找到你的家："+data[0]);
                return;
            }
            World world = Bukkit.getWorld(homeData1.WordMame);
            if(world==null){
                player.sendRawMessage("你的家可能已经失效！");
                return;
            }
            //同步传送玩家到家
            KTG2_experience.ktg2_experience.getServer().getScheduler().runTask(KTG2_experience.ktg2_experience, () -> player.teleport(new Location(world,homeData1.X,homeData1.Y,homeData1.Z,(float)homeData1.P,(float)homeData1.Q)));
        });
    }
}
