package cn.pang.winemusic.app;

import android.app.Application;
import android.util.Log;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("info","启动应用");
    }
}
