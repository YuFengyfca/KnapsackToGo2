package cn.jji8.KnapsackToGo2.spigot.synchronization;

public class LoadPlayerData {
    boolean cancel = false;
    /**
     * 获取释放被取消
     * */
    public boolean isCancel() {
        return cancel;
    }
    /**
     * 设置取消状态
     * */
    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
