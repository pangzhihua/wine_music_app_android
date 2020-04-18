package cn.pang.winemusic.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.pang.winemusic.MainActivity;

public class MusicService extends Service {


    private MediaPlayer player;//声明一个MediaPlayer对象

    private PlayBinder playBinder = new PlayBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return playBinder;
    }

    //创建服务
    @Override
    public void onCreate() {
        // 当player对象为空时
        if (player == null) {
            player = MediaPlayer.create(MusicService.this, Uri
                    .parse("https://webfs.yun.kugou.com/201911022257/947ed3a48d319b99e0af9b18e29118a5/G177/M02/16/07/UYcBAF2y_wqAWD8WAD38bClewhw535.mp3"));//实例化对象，通过播放本机server上的一首音乐

//            player.prepareAsync();

            player.setLooping(false);//设置不循环播放
        }
        super.onCreate();
    }

    //销毁服务
    @Override
    public void onDestroy() {
        //当对象不为空时
        if (player != null) {
            player.stop();//停止播放
            player.release();//释放资源
            player = null;//把player对象设置为null
        }
        super.onDestroy();
    }

    //開始服务
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO 自己主动生成的方法存根
        Bundle b = intent.getExtras();//获取到从MainActivity类中传递过来的Bundle对象
        int op = b.getInt("msg");//再获取到MainActivity类中op的值
        switch (op) {
            case 1://当op为1时。即点击播放button时
                play();//调用play()方法
                break;
            case 2://当op为2时，即点击暂停button时
                pause();//调用pause()方法
                break;
            case 3://当op为3时。即点击停止button时
                stop();//调用stop()方法
                break;
            default:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //停止播放音乐方法
    private void stop() {
        // 当player对象不为空时
        if (player != null) {
            player.seekTo(0);//设置从头開始
            player.stop();//停止播放
            try {
                player.prepare();//预载入音乐
            } catch (IllegalStateException e) {
                // TODO 自己主动生成的 catch 块
                e.printStackTrace();
            } catch (IOException e) {
                // TODO 自己主动生成的 catch 块
                e.printStackTrace();
            }
        }
    }

    //暂停播放音乐方法
    private void pause() {
        // 当player对象正在播放时而且player对象不为空时
        if (player.isPlaying() && player != null) {
            player.pause();//暂停播放音乐
        }
    }

    //播放音乐方法
    private void play() {
        // 当player对象不为空而且player不是正在播放时
        if (player != null && !player.isPlaying()) {
            player.start();//開始播放音乐
            updateSeekBar();
        }
    }

    public void updateSeekBar() {
        final int duration = player.getDuration();
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int currentPosition = player.getCurrentPosition();

                Message msg = Message.obtain();
                Bundle bundle = new Bundle(); // map
                bundle.putInt("duration", duration);
                bundle.putInt("currentPosition", currentPosition);
                bundle.putBoolean("isPlaying", player.isPlaying());

                msg.setData(bundle);
                MainActivity.mHandler.sendMessage(msg);
            }
        };

        timer.schedule(timerTask, 100, 1000);
        // 监听音乐播放完毕
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("歌曲播放完成了");
                timer.cancel();
                timerTask.cancel();
            }
        });
    }

    //实现指定播放的位置
    public void callSeekTo(int position) {
        player.seekTo(position);
    }

    public class PlayBinder extends Binder {

        public void seekTo(int position) {
            //调用办证的方法
            callSeekTo(position);
        }
    }
}
