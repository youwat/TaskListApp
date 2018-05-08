package com.example.watanabetakeshi.myapplication.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// テーブルごとにoverride
abstract class MyDbHelper(context: Context, version : Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, version)
{
    companion object {
        public val DATABASE_NAME = "MyDb.db" // SQLiteファイル名を指定
    }
    // SQLite ファイルが存在しない場合や VERSION が変更された際に実行する SQL を定義する
    abstract val createQuery : String
    // VERSIONが更新された場合、既存テーブルを削除する
    abstract val dropQuery : String

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(dropQuery)
        onCreate(db)
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createQuery)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}