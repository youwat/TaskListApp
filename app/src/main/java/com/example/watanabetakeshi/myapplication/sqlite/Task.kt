package com.example.watanabetakeshi.myapplication.sqlite
import android.content.Context
import com.example.watanabetakeshi.myapplication.contract.SqliteContract

class Task(context: Context) : MyDbHelper(context, VERSION) {
    companion object {
        val VERSION = 3
    }
    override val createQuery =
"""
    create table ${SqliteContract.TaskTable.TABLE_NAME} (
        ${SqliteContract.TaskTable._ID} integer primary key autoincrement,
        ${SqliteContract.TaskTable.NAME} varchar(256) not null,
        ${SqliteContract.TaskTable.NOTE} text not null,
        ${SqliteContract.TaskTable.IS_CHECKED} tinyint(2),
        ${SqliteContract.TaskTable.CREATED_AT} datetime not null,
        ${SqliteContract.TaskTable.UPDATE_AT} datetime not null
    )
"""
    override val dropQuery =
"""
    drop table if exists ${SqliteContract.TaskTable.TABLE_NAME}
"""
}