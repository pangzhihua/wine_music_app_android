package cn.pang.winemusic.common.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDataBase {

    private static final String TAG = "BaseDataBase";

    /**
     * Database Name
     */
    private static final String DATABASE_NAME = "redis_min";

    /**
     * Database Version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Table Name
     */
    private static final String DATABASE_TABLE = "tb_redis";

    /**
     * Table columns
     */
    public static final String KEY_NAME = "name";
    public static final String KEY_VAL = "val";
    public static final String KEY_ROWID = "_id";

    /**
     * Database creation sql statement
     */
    private static final String CREATE_TABLE =
            "create table " + DATABASE_TABLE + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, " + KEY_VAL + " text not null);";

    /**
     * Context
     */
    private final Context mCtx;

    private BaseDataBase.DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Inner private class. Database Helper class for creating and updating database.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * onCreate method is called for the 1st time when database doesn't exists.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "Creating DataBase: " + CREATE_TABLE);
            db.execSQL(CREATE_TABLE);
        }

        /**
         * onUpgrade method is called when database version changes.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public BaseDataBase(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * This method is used for creating/opening connection
     *
     * @return instance of DatabaseUtil
     * @throws SQLException
     */
    public BaseDataBase open() throws SQLException {
        mDbHelper = new BaseDataBase.DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * This method is used for closing the connection.
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * This method is used to create/insert new record record.
     *
     * @param name
     * @param value
     * @return long
     */
    public long insert(String name, String value) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_VAL, value);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * This method will delete record.
     *
     * @param rowId
     * @return boolean
     */
    public boolean delete(long rowId) {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * This method will deleteAll record.
     *
     * @return
     */
    public boolean deleteAll() {
        return mDb.delete(DATABASE_TABLE, " 1 ", null) > 0;
    }

    /**
     * This method will return Cursor holding all the records.
     *
     * @return Cursor
     */
    public Cursor fetchAll() {
        return mDb.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME,
                KEY_VAL}, null, null, null, null, null);
    }

    /**
     * This method will return Cursor holding the specific record.
     *
     * @param id
     * @return Cursor
     * @throws SQLException
     */
    public Cursor fetch(long id) throws SQLException {
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[]{KEY_ROWID,
                                KEY_NAME, KEY_VAL}, KEY_ROWID + "=" + id, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * 根据name查询数据
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public Cursor fetch(String name) throws SQLException {
        Cursor mCursor =
                mDb.query(true, DATABASE_TABLE, new String[]{KEY_ROWID,
                                KEY_NAME, KEY_VAL}, KEY_NAME + "='" + name+"'", null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * This method will update record.
     *
     * @param id
     * @param name
     * @param value
     * @return boolean
     */
    public boolean update(int id, String name, String value) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_VAL, value);
        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + id, null) > 0;
    }


}
