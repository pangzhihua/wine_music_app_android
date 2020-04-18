package cn.pang.winemusic.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MyMusicSevice extends Service {

    private MediaPlayer mediaPlayer;

    MyMusicSevice (){}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
