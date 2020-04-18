package cn.pang.winemusic.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pang.winemusic.Adapters.KSongListAdapter;
import cn.pang.winemusic.R;
import cn.pang.winemusic.common.MyHttp;
import cn.pang.winemusic.models.Song;
import cn.pang.winemusic.myviews.CustomTitleBar;
import cn.pang.winemusic.myviews.LoadingDialog;
import cn.pang.winemusic.utils.OkHttpManager;
import cn.pang.winemusic.utils.OkHttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class KSongListActivity extends Activity {

    private String TAG = "info";

    private RecyclerView mRecycleView;
    private KSongListAdapter kSongListAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private List<Song> mList = new ArrayList<Song>();


    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //实例化CustomTitleBar 传递相应的参数
        CustomTitleBar ct = new CustomTitleBar();
        ct.getTitleBar(this, "推荐新歌");

        setContentView(R.layout.activity_ksong_list);
        Log.i("info", "创建Activity");

        if (!this.isFinishing()) {
            loadingDialog = LoadingDialog.getInstance(this);//显示
            loadingDialog.show();
        }

        OkHttpManager okHttpManager = OkHttpManager.getInstance();
        okHttpManager.doGet("http://m.kugou.com/?json=true", new OkHttpManager.NetWorkCallBack() {
            @Override
            public void onSuccess(String res) {
                show(res);
            }

            @Override
            public void onFail(String str) {

            }
        });

    }

    private void show(final String result) {
        runOnUiThread(() -> {
            Log.i(TAG, result);
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(result);
            mList = new ArrayList<Song>();
            mRecycleView = findViewById(R.id.k_song_list_recycler_id);
            //初始化数据
            initData(jsonObject);
            //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
            mLinearLayoutManager = new LinearLayoutManager(KSongListActivity.this, LinearLayoutManager.VERTICAL, false);
            //创建适配器，将数据传递给适配器
            kSongListAdapter = new KSongListAdapter(KSongListActivity.this, mList);
            //设置布局管理器
            mRecycleView.setLayoutManager(mLinearLayoutManager);
            //设置适配器adapter
            mRecycleView.setAdapter(kSongListAdapter);

            loadingDialog.dismiss();
        });
    }

    public void initData(JsonObject jsonObject) {
        JsonArray dataList = jsonObject.getAsJsonArray("data");
        for (JsonElement el : dataList) {
            Log.i(TAG, el.toString());
            JsonObject json = el.getAsJsonObject();
            Song song = new Song();
            song.setName(json.get("remark").getAsString());
            song.setRemark(json.get("filename").getAsString());
            mList.add(song);
        }
    }
}
