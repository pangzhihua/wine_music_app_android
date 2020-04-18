package cn.pang.winemusic.services;

public interface Iservice {
    //把想暴露的方法都定义在接口中
    public void callPlayMusic();

    public void callPauseMusic();

    public void callRePlayMusic();

    public void stopMusic();

    public void callSeekTo(int position);
}
