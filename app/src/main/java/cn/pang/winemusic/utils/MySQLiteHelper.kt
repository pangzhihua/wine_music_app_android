package cn.pang.winemusic.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySQLiteHelper constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, name, factory, version) {


    private val CREATE_STUDENT = "create table student(id integer primary key autoincrement,name text,age integer)"
    private val CREATE_BOOK = "create table book(id integer primary key autoincrement,name text,price integer)"


    //第一次创建才会执行onCreate,无法完成升级
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_STUDENT)
        db?.execSQL(CREATE_BOOK)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        when (oldVersion) {
            1 -> db?.execSQL(CREATE_BOOK)
            2 -> db?.execSQL("alter table book add column publishdate integer")
        }
    }

}
