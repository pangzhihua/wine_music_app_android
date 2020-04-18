package cn.pang.winemusic.common.inters;

import android.database.sqlite.SQLiteDatabase;

public interface OnSqliteUpdateListener {

    public void onSqliteUpdateListener(SQLiteDatabase db, int oldVersion, int newVersion);
}
