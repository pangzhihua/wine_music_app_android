package cn.pang.winemusic;

import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pang.winemusic.Adapters.SongAdapter;
import cn.pang.winemusic.Adapters.SongSheetAdapter;
import cn.pang.winemusic.activitys.KSongListActivity;
import cn.pang.winemusic.activitys.SongSheetActivity;
import cn.pang.winemusic.models.Song;
import cn.pang.winemusic.models.SongSheet;
import cn.pang.winemusic.services.MusicService;


public class MainActivity extends AppCompatActivity {

    private MusicService.PlayBinder playBinder;
    private List<SongSheet> songSheetDataList = new ArrayList<>();
    private List<Song> bottomMusicDataList = new ArrayList<>();
    private int op = -1;//设置中间变量op

    private static SeekBar sb_main_bar;
    private static ImageView imageButton;

    public static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //获取我们携带的数据
            Bundle data = msg.getData();
            //获取歌曲的总时长 和 当前进度
            int duration = data.getInt("duration");
            int currentPosition = data.getInt("currentPosition");
            boolean isPlaying = data.getBoolean("isPlaying");
            sb_main_bar.setMax(duration);
            sb_main_bar.setProgress(currentPosition);

            if (isPlaying) {
                imageButton.setImageResource(R.drawable.pause_icon_64);
            } else {
                imageButton.setImageResource(R.drawable.play_icon_64);
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化fresco
        Fresco.initialize(this);

        //去掉顶部标题
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        //获取元素
        ListView songSheetList = findViewById(R.id.song_sheet_list);
        ImageView myRecordListBtn = findViewById(R.id.my_record_list_btn);
        LinearLayout linearSetKSongBtn = findViewById(R.id.my_list_set_k_song_btn);
        sb_main_bar = findViewById(R.id.my_record_seekBar);
        imageButton = findViewById(R.id.my_record_play_pause);

        Intent playIntent = new Intent(MainActivity.this, MusicService.class);//实例化一个Intent对象
        //绑定服务
        bindService(playIntent, serviceConnection, Service.BIND_AUTO_CREATE);

        //给进度条设置滑动监听
        sb_main_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.print("改变进度条");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                playBinder.seekTo(seekBar.getProgress());
//                //获取当前进度条的位置
//                int currentPosition = seekBar.getProgress();
//                //跳转到某个位置进行播放
//                mediaPlayer.seekTo(currentPosition);
            }
        });


        //加载歌单列表
        initSongSheet();
        SongSheetAdapter songSheetAdapter = new SongSheetAdapter(MainActivity.this, R.layout.my_list_song_item, songSheetDataList);
        songSheetList.setAdapter(songSheetAdapter);
        //设置 歌单 点击事件
        songSheetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SongSheet songSheet = songSheetDataList.get(i);
                Intent intent = new Intent(MainActivity.this, SongSheetActivity.class);
                intent.putExtra("songSheetId",songSheet.getImageId());
                startActivity(intent);
            }
        });

        myRecordListBtn.setOnClickListener((view) -> {
            Log.i("info", "出来列表");
            showDialog(MainActivity.this, R.layout.player_bottom_list_layout);
        });

        linearSetKSongBtn.setOnClickListener((view) -> {
            Log.i("info", "进入K歌列表（新歌榜单）");
            Intent intent = new Intent(MainActivity.this, KSongListActivity.class);
            startActivity(intent);
        });
        imageButton.setOnClickListener((view) -> {
//            playOrPause();
            if (op == 1) {
                op = 2;
                imageButton.setImageResource(R.drawable.play_icon_64);
            } else {
                op = 1;
                imageButton.setImageResource(R.drawable.pause_icon_64);
            }
            Bundle bundle = new Bundle();//实例化一个Bundle对象
            bundle.putInt("msg", op);//把op的值放入到bundle对象中
            playIntent.putExtras(bundle);//再把bundle对象放入intent对象中
            startService(playIntent);//开启这个服务
        });


    }


    private void initSongSheet() {
        SongSheet songSheet1 = new SongSheet("我喜欢", R.drawable.item_defual, 3);
        songSheetDataList.add(songSheet1);
        SongSheet songSheet2 = new SongSheet("默认歌单", R.drawable.item_defual, 10);
        songSheetDataList.add(songSheet2);
    }

    private void initBottomMusicList() {
        Song song = new Song();
        song.setName("大姐夫");
        song.setId(1);
        song.setImageId(R.drawable.item_defual);
        song.setRemark("到付款");
        song.setImageUrl("https://img2.woyaogexing.com/2019/10/30/d5547c4a7047445a81efd4c67a820203!400x400.jpeg");
        bottomMusicDataList.add(song);
        Song song1 = new Song();
        song1.setName("打发啊-打发");
        song1.setId(1);
        song1.setImageId(R.drawable.item_defual);
        song1.setRemark("sdf的发");
        bottomMusicDataList.add(song1);
    }

    private void showDialog(Context context, @LayoutRes int resource) {

        //设置要显示的view
        View view = View.inflate(context, resource, null);
        //此处可按需求为各控件设置属性
//        view.findViewById(R.id.dialog_voicegame_share_cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
        Dialog dialog = new Dialog(context, R.style.dialog_style);
        dialog.setContentView(view);

        //加载列表数据
        if (bottomMusicDataList.size() <= 0) {
            initBottomMusicList();
        }
        SongAdapter songAdapter = new SongAdapter(context, R.layout.player_bottom_list_item, R.layout.player_bottom_list_item2, bottomMusicDataList, 1);
        ListView bottomSongList = view.findViewById(R.id.player_bottom_list_layout_id);
        bottomSongList.setAdapter(songAdapter);


        Window window = dialog.getWindow();

        //设置弹出窗口大小
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //设置显示位置
        window.setGravity(Gravity.BOTTOM);
        //设置动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        dialog.show();

    }

    //监视服务的状态
    private ServiceConnection serviceConnection = new ServiceConnection() {

        //当服务连接成功调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取中间人对象
            playBinder = (MusicService.PlayBinder) service;
        }

        //失去连接
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}

