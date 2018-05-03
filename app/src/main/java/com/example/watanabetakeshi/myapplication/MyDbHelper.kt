package com.example.watanabetakeshi.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.watanabetakeshi.myapplication.SqliteContract.MyTable

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object {
        // スキーマに変更があった場合VERSIONをインクリメント
        public val DATABASE_VERSION = 1
        // SQLiteファイル名を指定
        public val DATABASE_NAME = "MyDb.db"
        // SQLite ファイルが存在しない場合や VERSION が変更された際に実行する SQL を定義します。
        private val SQL_CREATE_TABLE = "CREATE TABLE " +
                MyTable.TABLE_NAME + " (" +
                MyTable._ID + " INTEGER PRIMARY KEY," +
                MyTable.COLUMN_NAME_INT_COL + " INTEGER," +
                MyTable.COLUMN_NAME_STR_COL + " TEXT)"
        private val SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + MyTable.TABLE_NAME
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DROP_TABLE)
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}