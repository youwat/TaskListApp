package com.example.watanabetakeshi.myapplication

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import java.util.*


class TaskList()
{
    // Viewと紐付けるデータ配列
    var list = ArrayList<Map<String, String>>()

    var createId = fun(): () -> Int {
        var id = 0
        return {id++}
    }()

    private var activityContext: Context ?= null

    private var taskListView: ListView ?= null

    fun init(context: Context, taskListView : ListView) {
        // テストデータいくつか。
        list.add(mapOf("id" to "1", "taskName" to "hogehoge"))
        list.add(mapOf("id" to "2", "taskName" to "hogehoge2"))
        list.add(mapOf("id" to "3", "taskName" to "hogehoge3"))

        this.activityContext = context
        this.taskListView = taskListView
        setAdapter()
    }

    /**
     * アダプタをListViewにセットする
     */
    fun setAdapter() {
        // アダプターの生成
        var adapter = SimpleAdapter(
                activityContext, list,
                R.layout.task_list_item, arrayOf("id", "taskName"), intArrayOf(R.id.taskId, R.id.taskName))
        taskListView?.adapter = adapter // ロジック的にNULLになることはない
    }

}