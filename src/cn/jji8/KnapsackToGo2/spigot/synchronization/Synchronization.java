package cn.jji8.KnapsackToGo2.spigot.synchronization;

import org.bukkit.entity.Player;

public abstract class Synchronization {
    /**
     * 同步的名字,请保证每次获取到的名字相同。
     * */
    protected abstract String getName();
    /**
     * 加载玩家数据，从字符串中加载玩家的数据
     * @param player 加载数据的玩家
     * @param Data 数据的string形式
     * */
    protected abstract void loadData(Player player, String Data);
    /**
     * 保存玩家数据,将玩家的数据已字符串形式返回
     * @param player 保存数据的玩家
     * */
    protected abstract String saveData(Player player);
    /**
     * 比较两个执行器相同
     * */
    @Override
    public boolean equals(Object o){
        if(o instanceof Synchronization){
            String q = ((Synchronization)o).getName();
            String w = getName();
            if(q!=null){
                return q.equals(w);
            }
            return w == null;
        }
        return false;
    }
}
