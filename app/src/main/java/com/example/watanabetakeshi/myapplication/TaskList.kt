package com.example.watanabetakeshi.myapplication

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import java.util.*


class TaskList(var activityContext : Context, var taskListView : ListView)
{
    // Viewと紐付けるデータ配列
    var list = ArrayList<Map<String, String>>()

    var createId = fun(): () -> Int {
        var id = 0
        return {id++}
    }()
    init {
        // ローカルDBのデータを取得する
        setFromLoacalData()
        // アダプターを設定
        setAdapter()
    }

    fun setFromLoacalData() {
        // TODO: debug テストデータいくつか。
        for(i in 1..30) {
            val addMap = mapOf("id" to "${i}", "taskName" to "hogehoge${i}")
            list.add(addMap)
        }
    }

    /**
     * アダプタをListViewにセットする
     */
    fun setAdapter() {
        // アダプターの生成
        val adapter = SimpleAdapter(
                activityContext, list,
                R.layout.task_list_item, arrayOf("taskName"), intArrayOf(R.id.taskName))
        taskListView.adapter = adapter // ロジック的にNULLになることはない
    }

    fun add(taskName: String, checked: Boolean) {
        list.add(mapOf("taskName" to "add"))
        taskListView.invalidateViews()
    }

    fun changeCheckBox(taskId: Int, checked: Boolean)
    {
    }
}