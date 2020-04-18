package cn.pang.winemusic.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.pang.winemusic.R;
import cn.pang.winemusic.common.data.BaseDataBase;
import cn.pang.winemusic.utils.DatabaseUtil;

public class SongSheetActivity extends AppCompatActivity {

    private final String TAG = "SongSheetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_sheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //接受页面传来的参数
        Intent intent = getIntent();
        Integer songSheetId = intent.getIntExtra("songSheetId", 0);

        //--------------------------------------

        Button xieru = findViewById(R.id.xieru);
        Button duqu = findViewById(R.id.diqu);

        BaseDataBase dataBase = new BaseDataBase(this);
        dataBase.open();

        xieru.setOnClickListener((v) -> {
            String json = "{\"name\":\"pangzhihua\"}";
            dataBase.insert("test", json);
            Log.i(TAG, "add over");
        });
        duqu.setOnClickListener((v) -> {
            Cursor cursor = dataBase.fetch("test");
            Log.i(TAG, cursor.getString(2));
        });
    }
}
