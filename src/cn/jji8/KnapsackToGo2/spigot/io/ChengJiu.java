package cn.jji8.KnapsackToGo2.spigot.io;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 同步玩家成就
 * */
public class ChengJiu extends Io {

    public ChengJiu(Plugin plugin) {
        super(plugin);
    }

    /**
     * 同步的名字,请保证每次获取到的名字相同。
     */
    @Override
    protected String getName() {
        return "成就";
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
            Iterator<Advancement> advancementList = Bukkit.advancementIterator();
            while (advancementList.hasNext()) {
                Advancement advancement = advancementList.next();
                String advancementName = advancement.getKey().toString();//成就的名字
                AdvancementProgress advancementProgress = player.getAdvancementProgress(advancement);
                List<String> complete = wanjiawenjian.getStringList(advancementName+".达成的条件");
                List<String> noComplete = wanjiawenjian.getStringList(advancementName+".未达到的条件");
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    for(String a:complete){
                        advancementProgress.awardCriteria(a);
                    }
                    for(String a:noComplete){
                        advancementProgress.revokeCriteria(a);
                    }
                });
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
        Iterator<Advancement> advancementList = Bukkit.advancementIterator();
        while (advancementList.hasNext()) {
            Advancement advancement = advancementList.next();
            String advancementName = advancement.getKey().toString();//成就的名字

            AdvancementProgress advancementProgress = player.getAdvancementProgress(advancement);
            Collection<String> completeColl = advancementProgress.getAwardedCriteria();//已到达的全部条件
            ArrayList<String> completeList = new ArrayList<>(completeColl);
            Collection<String> criteriaColl = advancementProgress.getRemainingCriteria();//未达到的全部条件
            ArrayList<String> criteriaList = new ArrayList<>(criteriaColl);
            wanjiawenjian.set(advancementName+".达成的条件",completeList);
            wanjiawenjian.set(advancementName+".未达到的条件",criteriaList);
        }
        return wanjiawenjian.saveToString();
    }
}
