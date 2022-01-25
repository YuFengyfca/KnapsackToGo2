package cn.jji8.KnapsackToGo2.spigot.io;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
* 我负责药水效果
* */
public class YaoSui extends Io {

    public YaoSui(Plugin plugin) {
        super(plugin);
    }

    /**
     * 同步的名字,请保证每次获取到的名字相同。
     */
    @Override
    protected String getName() {
        return "药水效果";
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
            List List = wanjiawenjian.getMapList("药水效果");
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                PotionEffectType[] T = PotionEffectType.values();
                for(int i=0;i<T.length;i++){
                    if(T[i]!=null){
                        player.removePotionEffect(T[i]);
                    }
                }
                for(int i = 0;i<List.size();i++){
                    player.addPotionEffect(new PotionEffect((Map<String, Object>) List.get(i)));
                }
            });
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
        YamlConfiguration wanjiawenjian = new YamlConfiguration();
        Iterator biao = player.getActivePotionEffects().iterator();
        ArrayList ArrayList = new ArrayList();
        for(int i = 0;biao.hasNext();i++){
            ArrayList.add(((PotionEffect)biao.next()).serialize());
        }
        wanjiawenjian.set("药水效果",ArrayList);
        return wanjiawenjian.saveToString();
    }
}
