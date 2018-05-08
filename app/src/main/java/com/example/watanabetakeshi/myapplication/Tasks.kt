package com.example.watanabetakeshi.myapplication

import android.content.Context
import android.util.Log
import android.widget.ListView
import com.example.watanabetakeshi.myapplication.sqlite.*
import com.example.watanabetakeshi.myapplication.contract.*
import android.content.ContentValues
import com.example.watanabetakeshi.myapplication.adapter.TaskListViewItem
import com.example.watanabetakeshi.myapplication.adapter.TaskListViewAdapter


class Tasks(val activityContext : Context, var taskListView : ListView?)
{
    private val TAG = "Tasks"
    private val _task = Task(activityContext)
    private val _reader = _task.readableDatabase
    private val _writer = _task.writableDatabase

    // Viewと紐付けるデータ配列
    val list : MutableList<TaskListViewItem> = mutableListOf()

    var createId = fun(): () -> Int {
        var id = 0
        return {id++}
    }()
    init {
        loadData() // ローカルDBのデータを取得する
        setAdapter() // アダプターを設定
    }

    private fun loadData()
    {
        // SELECT
        val projection = arrayOf<String>(// SELECT する列
                SqliteContract.TaskTable._ID,
                SqliteContract.TaskTable.NAME,
                SqliteContract.TaskTable.NOTE,
                SqliteContract.TaskTable.IS_CHECKED
        )
        val selection = "1" // WHERE 句
        val sortOrder = SqliteContract.TaskTable._ID // ORDER 句
        val cursor = _reader.query(
                SqliteContract.TaskTable.TABLE_NAME, // The table to query
                projection,                          // The columns to return
                selection,                           // The columns for the WHERE clause
                null,                   // don't group the rows
                null, null,         // don't filter by row groups
                sortOrder                           // The sort order
        )// The values for the WHERE clause
        var i=0
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(SqliteContract.TaskTable.NAME))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(SqliteContract.TaskTable._ID))
            val checked = cursor.getInt(cursor.getColumnIndexOrThrow(SqliteContract.TaskTable.IS_CHECKED))
            list.add(TaskListViewItem(id.toLong(), checked != 0, name))
        }
        cursor.close()
    }

    /**
     * アダプタをListViewにセットする
     */
    fun setAdapter() {
        // アダプターの生成
        val adapter = TaskListViewAdapter(activityContext, R.layout.task_list_item, list)
        taskListView!!.adapter = adapter
    }

    fun addAndRefresh(taskName: String, checked: Boolean) {
        // 0件から1件に更新した場合、
        add(taskName, checked)
        taskListView!!.invalidateViews()
    }

    fun add(taskName:String, checked: Boolean) {
        // insert 適当に
        val values = ContentValues()
        values.put(SqliteContract.TaskTable.NAME, taskName)
        values.put(SqliteContract.TaskTable.NOTE, "no data")
        values.put(SqliteContract.TaskTable.IS_CHECKED, false)
        values.put(SqliteContract.TaskTable.CREATED_AT, "2018-01-01 00:00:00")
        values.put(SqliteContract.TaskTable.UPDATE_AT, "2018-01-01 00:00:00")
        val id = _writer.insert(SqliteContract.TaskTable.TABLE_NAME, null, values)
        if(id != -1L) list.add(TaskListViewItem(id, checked, taskName))
    }

    fun isChecked(position: Int) : Boolean{
        return list[position].checkBoxState
    }
    fun checked(position: Int) {
        // データベースを編集
        val id = list[position].id.toString()
        val values = ContentValues()
        values.put(SqliteContract.TaskTable.IS_CHECKED, 1)
        val p = position.toString()
        _writer.update(SqliteContract.TaskTable.TABLE_NAME, values, "_id = ?", arrayOf(id))
        // adapterデータを変更
        list[position].checkBoxState = true
        taskListView!!.invalidateViews()
    }
    fun unChecked(position: Int) {
        // データベースを編集
        val id = list[position].id.toString()
        val values = ContentValues()
        values.put(SqliteContract.TaskTable.IS_CHECKED, 0)
        val p = position.toString()
        _writer.update(SqliteContract.TaskTable.TABLE_NAME, values, "_id = ?", arrayOf(id))
        // adapterデータを変更
        list[position].checkBoxState = false
        taskListView!!.invalidateViews()
    }
    fun delete(position: Int) {
        // データベースを削除
        val id = list[position].id.toString()
        val ret = _writer.delete(SqliteContract.TaskTable.TABLE_NAME, "_id = ?", arrayOf(id))
        Log.d("mydebug", "id = ${id} deleted record : ${ret}")
        // adapterデータを変更
        list.removeAt(position)
        taskListView!!.invalidateViews()
    }
}