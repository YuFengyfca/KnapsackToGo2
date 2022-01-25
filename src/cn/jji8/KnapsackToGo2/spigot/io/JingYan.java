package cn.jji8.KnapsackToGo2.spigot.io;


import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.math.BigDecimal;

/*
* 我负责经验
* */
public class JingYan extends Io {
    public JingYan(Plugin plugin) {
        super(plugin);
    }

    /**
     * 同步的名字,请保证每次获取到的名字相同。
     */
    @Override
    protected String getName() {
        return "经验";
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
            if(wanjiawenjian.contains("等级")){
                player.setLevel(wanjiawenjian.getInt("等级"));
            }
            if(wanjiawenjian.contains("升级进度")){
                player.setExp(new BigDecimal(wanjiawenjian.getString("升级进度")).floatValue());
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
        wanjiawenjian.set("等级",player.getLevel());
        wanjiawenjian.set("升级进度",player.getExp());
        return wanjiawenjian.saveToString();
    }
}
