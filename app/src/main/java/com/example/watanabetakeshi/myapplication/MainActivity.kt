package com.example.watanabetakeshi.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.taskListView)
        // データの準備
        val taskList = TaskList()
        listView.emptyView = findViewById(R.id.emptyView)
        taskList.init(this, listView)
    }
}
