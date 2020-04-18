package cn.pang.winemusic.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class PlayingMusicService extends Service {
    public MediaPlayer mediaPlayer = new MediaPlayer();
    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {

        public PlayingMusicService getService() {
            return PlayingMusicService.this;//找到后台服务的指针，返回后台服务实例
        }
    }

    public PlayingMusicService() {
        try {
            mediaPlayer.setDataSource("https://webfs.yun.kugou.com/201911022257/947ed3a48d319b99e0af9b18e29118a5/G177/M02/16/07/UYcBAF2y_wqAWD8WAD38bClewhw535.mp3");//绑定播放的歌曲
            mediaPlayer.prepare();//进入就绪状态
            mediaPlayer.setLooping(false);//设置循环播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();//停止歌曲播放
            mediaPlayer.release();//释放mediaPlayer资源
        }
    }

    public void playORpuase() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();//暂停
        } else {
            mediaPlayer.start();//开始
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();//停止
            try {
                mediaPlayer.prepare();//就绪
                mediaPlayer.seekTo(0);//设置歌曲回到最开始
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}