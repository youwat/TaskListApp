package com.example.watanabetakeshi.myapplication

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView


class TaskList
{
    var list = mutableListOf<Task>()
    var createId = fun(): () -> Int {
        var id = 0
        return {id++}
    }()

    fun init() {
        // テストデータいくつか。
        list.add(Task(id = createId(), title = "test1", body = "本文1"))
        list.add(Task(id = createId(), title = "test2", body = "本文2"))
        list.add(Task(id = createId(), title = "test3", body = "本文3"))
    }

    fun setAdapter(context : Context, taskListView : ListView) {
        var items = getItemList()
        var adapter = ArrayAdapter<String>(
                context, R.layout.task_list_item, items
        )
        taskListView.adapter = adapter
    }

    private fun getItemList(): ArrayList<String> {
        var items = ArrayList<String>()
        for(i in 0..30) {
            items.add("items-$i")
        }
        return items
    }
}