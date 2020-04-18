package cn.pang.winemusic.common.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private final String TAG = "DbHelper";
    private String dateBaseName;
    private int dateBaseVersion;
    private String TableName;

//    private String CREATE_TABLE =
//            "create table " + TableName + " (" + KEY_ROWID + " integer primary key autoincrement, "
//                    + KEY_NAME + " text not null, " + KEY_VAL + " text not null);";

    DbHelper(Context context, String dateBaseName, int dateBaseVersion,String TableName) {
        super(context, dateBaseName, null, dateBaseVersion);
        this.dateBaseName = dateBaseName;
        this.dateBaseVersion = dateBaseVersion;
        this.TableName = TableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion);
    }
}
