package cn.jji8.KnapsackToGo2.spigot.synchronization;

import cn.jji8.KnapsackToGo2.spigot.KnapsackToGo2Spigot;
import cn.jji8.KnapsackToGo2.spigot.PlayerEvent.DataLoadComplete;
import cn.jji8.KnapsackToGo2.spigot.xdata.SpigotData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * 同步执行管理器
 * */
public class SynchronizationManager implements Listener,Runnable{
    /////////////////////////////////////////////////////////////////
    //设定值
    public static int waitingTime = 60;//等待时间
    /////////////////////////////////////////////////////////////////
    //同步执行器列表
    static Set<Synchronization> synchronizationList = new HashSet<>();
    //未完成同步的玩家集合
    public static Map<Player,LoadPlayerData> complete = new HashMap<>();
    /**
     * 添加一个同步执行器
     * */
    public static void addSynchronization(Synchronization synchronization){
        synchronizationList.add(synchronization);
    }
    /**
     * 为某玩家加载数据
     * */
    static void loadData(Player player,Tips.TipsMainger tipsMainger){
        for(Synchronization synchronization:synchronizationList){
            tipsMainger.setNews(",加载"+synchronization.getName());
            try {
                synchronization.loadData(player,SpigotData.get(player.getUniqueId().toString(),synchronization.getName()));
            }catch (Throwable e){
                e.printStackTrace();
                KnapsackToGo2Spigot.KnapsackToGo2Spigot.getLogger().warning("为玩家"+player.getName()+"加载数据"+synchronization.getName()+"时发生错误。");
            }
        }
    }
    /**
     * 为某玩家保存数据
     * */
    public static void saveData(Player player){
        for(Synchronization synchronization:synchronizationList){
            try {
                SpigotData.set(player.getUniqueId().toString(),synchronization.getName(),synchronization.saveData(player));
            }catch (Throwable e){
                e.printStackTrace();
                KnapsackToGo2Spigot.KnapsackToGo2Spigot.getLogger().warning("为玩家"+player.getName()+"保存数据"+synchronization.getName()+"时发生错误。");
            }
        }
    }
    /**
     * 柱塞线程，等待玩家被其他服务器解锁
     * */
    static void waitFor(Player player){
        try{Thread.sleep(500);}catch(InterruptedException ignored){}
        for (int i=0;i<=waitingTime;i++){
            String lock = SpigotData.get(player.getUniqueId().toString(),"lock");
            if(!"true".equals(lock)){
                break;
            }
            try{Thread.sleep(1000);}catch(InterruptedException ignored){}
        }
    }
    /**
     * 给玩家上锁
     * */
    public static void lock(Player player){
        SpigotData.set(player.getUniqueId().toString(),"lock","true");
    }
    /**
     * 解锁，玩家退出服务器保存完数据后解锁
     * */
    public static void unlocking(Player player){
        SpigotData.set(player.getUniqueId().toString(),"lock","false");
    }
    ////////////////////////////////////////////////////
    //下面全是实例方法和字段

    //插件的主类
    Plugin plugin;
    /**
     * 一个构造器
     * */
    public SynchronizationManager(Plugin plugin){
        this.plugin = plugin;
    }
    /**
     * 自动保存任务的方法
     * */
    @Override
    public void run() {
        Collection collection = plugin.getServer().getOnlinePlayers();
        for(Object player:collection){
            if(player instanceof Player){
                if(complete.containsKey(player)){
                    continue;
                }
                saveData((Player) player);
            }
        }
    }
    /**
     * 玩家进入服务器时触发
     * */
    @EventHandler
    public void Listener(PlayerJoinEvent a){
        LoadPlayerData loadPlayerData = new LoadPlayerData();
        complete.put(a.getPlayer(),loadPlayerData);
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            //给玩家提示信息
            Tips.TipsMainger tipsMainger = Tips.showTips(plugin,a.getPlayer(),",正在准备");
            //等待玩家解锁
            waitFor(a.getPlayer());
            //如果玩家在加载前已经退出，就不用加载了
            if(loadPlayerData.isCancel()){
                //设置玩家加载完成
                complete.remove(a.getPlayer());
                return;
            }
            //上锁
            lock(a.getPlayer());
            //加载数据
            loadData(a.getPlayer(),tipsMainger);
            //设置玩家加载完成
            complete.remove(a.getPlayer());
            //如果玩家在加载前已经退出，就解锁
            if(loadPlayerData.isCancel()){
                unlocking(a.getPlayer());
                return;
            }
            //关闭玩家的提示
            tipsMainger.cancel(true);
            //抛出事件
            Bukkit.getServer().getScheduler().runTask(plugin, () -> Bukkit.getPluginManager().callEvent(new DataLoadComplete(a.getPlayer())));
        });
    }
    /**
     * 玩家离开服务器时触发
     * */
    @EventHandler
    public void Listener(PlayerQuitEvent a){
        LoadPlayerData loadPlayerData = complete.get(a.getPlayer());
        if(loadPlayerData!=null){
            loadPlayerData.setCancel(true);
            return;
        }
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            saveData(a.getPlayer());
            unlocking(a.getPlayer());
        });
    }






    @EventHandler
    public void WanJiaYiDong(PlayerMoveEvent a){//玩家移动时
        if(complete.containsKey(a.getPlayer())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJiaDiuWuPing(PlayerDropItemEvent a){//玩家丢物品
        if(complete.containsKey(a.getPlayer())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJiaPoHuaiFangKuai(BlockBreakEvent a){//玩家破坏方块
        if(complete.containsKey(a.getPlayer())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJiaFangZhiFangKuai(BlockPlaceEvent a){//玩家放置方块
        if(complete.containsKey(a.getPlayer())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJIaQieHuanFuSou(PlayerSwapHandItemsEvent a){//玩家切换副手
        if(complete.containsKey(a.getPlayer())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void  WanJiaJiaoHu(PlayerInteractEvent a){//玩家与空气方块交互时
        if(complete.containsKey(a.getPlayer())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJIaDianJiShiTi(PlayerInteractEntityEvent a){//玩家点击实体时
        if(complete.containsKey(a.getPlayer())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJIaDianJiShiTi(EntityDamageByEntityEvent a){//实体攻击实体
        if(complete.containsKey(a.getEntity())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJIaDianJiShiTi(EntityDamageByBlockEvent a){//实体受到方块伤害
        if(complete.containsKey(a.getEntity())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJIaDianJiShiTi(EntityPickupItemEvent a){//实体捡起物品
        if(complete.containsKey(a.getEntity())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJIaDianJiShiTi(InventoryClickEvent a){//点击物品栏
        if(complete.containsKey(a.getWhoClicked())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void WanJIaDianJiShiTi(AsyncPlayerChatEvent a){//聊天
        if(complete.containsKey(a.getPlayer())){
            a.setCancelled(true);
        }
    }
    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent a){//命令
        if(complete.containsKey(a.getPlayer())){
            a.setCancelled(true);
        }
    }
}
