package cn.jji8.KnapsackToGo2.handle;

import cn.jji8.KnapsackToGo2.FieldMame;

import java.util.HashMap;

/**
 * 处理home的数据
 * */
public class HomeAllData {
    /**
     * 代表一个家
     * */
    public static class HomeData{
        public String serverName,WordMame;
        public double X,Y,Z,P,Q;
        /**
         * 加载一个home的数据
         * */
        public HomeData(String data) throws Exception {
            String[] data1 = data.split(",");
            if(data1.length!=7){
                throw new Exception(FieldMame.Home_Data_Name +"数据损坏，无法加载。");
            }
            serverName = data1[0];
            WordMame = data1[1];
            try {
                X = Double.parseDouble(data1[2]);
                Y = Double.parseDouble(data1[3]);
                Z = Double.parseDouble(data1[4]);
                P = Double.parseDouble(data1[5]);
                Q = Double.parseDouble(data1[6]);
            }catch (NumberFormatException e){
                throw new Exception(FieldMame.Home_Data_Name +"数据损坏，无法加载。");
            }
        }
        /**
         * 构造一个家
         * */
        public HomeData(String serverName, String wordMame, double x, double y, double z, double p, double q) {
            this.serverName = serverName;
            WordMame = wordMame;
            X = x;
            Y = y;
            Z = z;
            P = p;
            Q = q;
        }
        /**
         * 将这个家保存为String
         * */
        @Override
        public String toString() {
            return serverName+","+WordMame+","+X+","+Y+","+Z+","+P+","+Q;
        }
    }
    public HashMap<String,HomeData> homeDataMap = new HashMap<>();
    /**
     * 加载一个home的数据
     * */
    public HomeAllData(String data){
        if(data==null){
            return;
        }
        String[] data1 = data.split(";");
        for (String s : data1) {
            String[] s1 = s.split(":");
            if(s1.length!=2){
                continue;
            }
            try {
                homeDataMap.put(s1[0],new HomeData(s1[1]));
            } catch (Exception ignored) {
            }
        }
    }
    /**
     * 获取某个家
     * */
    public HomeData getHomeData(String homeName){
        return homeDataMap.get(homeName);
    }
    /**
     * put某个家
     * */
    public void putHome(String name,HomeData homeData){
        homeDataMap.put(name,homeData);
    }
    /**
     * 删除某个家
     * */
    public HomeData removeHome(String name){
        return homeDataMap.remove(name);
    }
    /**
     * 获取家的数量
     * */
    public int quantity(){
        return homeDataMap.size();
    }
    /**
     * 将全部的家保存为String
     * */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String homeName : homeDataMap.keySet()) {
            stringBuilder.append(homeName);
            stringBuilder.append(":");
            stringBuilder.append(homeDataMap.get(homeName).toString());
            stringBuilder.append(";");
        }
        stringBuilder.append(" ");
        return stringBuilder.toString();
    }
}
