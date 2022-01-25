package cn.jji8.KnapsackToGo2.spigot.io;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;


/*
* 我负责玩家背包
* */
public class BeiBao extends Io {
    public BeiBao(Plugin plugin) {
        super(plugin);
    }
    /**
     * 同步的名字,请保证每次获取到的名字相同。
     */
    @Override
    protected String getName() {
        return "背包";
    }

    /**
     * 加载玩家数据，从字符串中加载玩家的数据
     */
    @Override
    protected void loadData(Player player, String Data) {
        if(Data==null){
            return;
        }
        try {
            YamlConfiguration wanjiawenjian = new YamlConfiguration();
            wanjiawenjian.loadFromString(Data);
            PlayerInventory PlayerInventory = player.getInventory();
            if(wanjiawenjian.contains("物品")|wanjiawenjian.contains("装备")|wanjiawenjian.contains("副手")){
                for(int i=0;i<36;i++){
                    PlayerInventory.setItem(i,wanjiawenjian.getItemStack("物品."+i));
                }
                PlayerInventory.setBoots(wanjiawenjian.getItemStack("装备.鞋子"));
                PlayerInventory.setChestplate(wanjiawenjian.getItemStack("装备.胸甲"));
                PlayerInventory.setHelmet(wanjiawenjian.getItemStack("装备.头盔"));
                PlayerInventory.setLeggings(wanjiawenjian.getItemStack("装备.护腿"));
                PlayerInventory.setItemInOffHand(wanjiawenjian.getItemStack("副手"));
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
        PlayerInventory PlayerInventory = player.getInventory();
        wanjiawenjian.set("装备.鞋子",PlayerInventory.getBoots());//鞋子
        wanjiawenjian.set("装备.胸甲",PlayerInventory.getChestplate());//胸甲
        wanjiawenjian.set("装备.头盔",PlayerInventory.getHelmet());//头盔
        wanjiawenjian.set("装备.护腿",PlayerInventory.getLeggings());//护腿
        wanjiawenjian.set("副手",PlayerInventory.getItemInOffHand());
        for(int i=0;i<36;i++){
            wanjiawenjian.set("物品."+i,PlayerInventory.getItem(i));
        }
        return wanjiawenjian.saveToString();
    }
}
