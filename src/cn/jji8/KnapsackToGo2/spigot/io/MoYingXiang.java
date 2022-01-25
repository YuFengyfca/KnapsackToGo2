package cn.jji8.KnapsackToGo2.spigot.io;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;


/*
* 我负责玩家末影箱
* */
public class MoYingXiang extends Io {

    public MoYingXiang(Plugin plugin) {
        super(plugin);
    }

    /**
     * 同步的名字,请保证每次获取到的名字相同。
     */
    @Override
    protected String getName() {
        return "末影箱";
    }


    /**
     * 加载玩家数据，从字符串中加载玩家的数据
     */
    @Override
    protected void loadData(Player player, String Data) {
        if(Data==null){
            return;
        }
        if(Data.length()<5){
            return;
        }
        try {
            YamlConfiguration wanjiawenjian = new YamlConfiguration();
            wanjiawenjian.loadFromString(Data);
            Inventory Inventory = player.getEnderChest();
            for(int i=0;i<27;i++){
                Inventory.setItem(i,wanjiawenjian.getItemStack("物品."+i));
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存玩家数据,将玩家的数据已字符串形式返回
     */
    @Override
    protected String saveData(Player player) {
        YamlConfiguration wanjiawenjian = new YamlConfiguration();
        Inventory Inventory = player.getEnderChest();
        for(int i=0;i<27;i++){
            wanjiawenjian.set("物品."+i,Inventory.getItem(i));
        }
        wanjiawenjian.set("这是有数据的哦","这是有数据的哦");
        return wanjiawenjian.saveToString();
    }
}
