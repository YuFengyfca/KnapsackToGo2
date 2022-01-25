package cn.jji8.KnapsackToGo2.spigot.io;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;


/*
* 我负责血量饱食度
* */
public class XueLiangBaoShiDu extends Io {

    public XueLiangBaoShiDu(Plugin plugin) {
        super(plugin);
    }

    /**
     * 同步的名字,请保证每次获取到的名字相同。
     */
    @Override
    protected String getName() {
        return "血量饱食度";
    }

    /**
     * 加载玩家数据，从字符串中加载玩家的数据
     *
     * @param player 加载数据的玩家
     * @param Data   数据的string形式
     */
    @Override
    protected void loadData(Player player, String Data) {
        if(Data==null){
            return;
        }
        try {
            YamlConfiguration wanjiawenjian = new YamlConfiguration();
            wanjiawenjian.loadFromString(Data);
            if(wanjiawenjian.contains("饥饿度")){
                player.setFoodLevel(wanjiawenjian.getInt("饥饿度"));
            }
            if(wanjiawenjian.contains("饱食度")){
                player.setSaturation((float)wanjiawenjian.getDouble("饱食度"));
            }
            if(wanjiawenjian.contains("最大血量")){
                Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(wanjiawenjian.getDouble("最大血量"));
            }
            if(wanjiawenjian.contains("血量")){
                player.setHealth(wanjiawenjian.getDouble("血量")<=0?1:wanjiawenjian.getDouble("血量"));
            }
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存玩家数据,将玩家的数据已字符串形式返回
     *
     * @param player 保存数据的玩家
     */
    @Override
    protected String saveData(Player player) {
        YamlConfiguration wanjiawenjian =  new YamlConfiguration();
        wanjiawenjian.set("饥饿度",player.getFoodLevel());
        wanjiawenjian.set("饱食度",player.getSaturation());
        wanjiawenjian.set("血量",player.getHealth());
        wanjiawenjian.set("最大血量", Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
        return wanjiawenjian.saveToString();
    }
}
