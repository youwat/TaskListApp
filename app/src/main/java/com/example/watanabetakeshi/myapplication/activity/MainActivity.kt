package com.example.watanabetakeshi.myapplication.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.watanabetakeshi.myapplication.R
import com.example.watanabetakeshi.myapplication.Tasks
import com.example.watanabetakeshi.myapplication.contract.CommonContract
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var taskList : Tasks? = null
    private var listView : ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/ コンポーネントにイベントをはる_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        //////////// タスクリスト
        Log.d(TAG, "タスクリストの初期データの準備")
        listView = findViewById<ListView>(R.id.taskListView)
        taskList = Tasks(this, listView)
        // listView.emptyView = findViewById(R.id.emptyView)
        listView!!.setOnItemClickListener { parent, view, position, id ->
            if (taskList!!.isChecked(position)) {
                taskList!!.unChecked(position)
            } else {
                taskList!!.checked(position)
            }
        }
        listView!!.setOnItemLongClickListener { parent, view, position, id ->
            if (taskList!!.isChecked(position)) {
                taskList!!.delete(position)
            } else {
                taskList!!.unChecked(position)
            }
            true
        }

        // 新規アクティビティ呼び出しのテストのために作った。
        // 同じ画面に追加テキストボックスを作る予定
        /////////// タスク追加ボタン
        val button = findViewById<Button>(R.id.addButton)
        button.setOnClickListener {
            // インテントは空
            try {
                startActivityForResult(Intent(this, AddTaskActivity::class.java),
                        CommonContract.Code.ADD_TASK)
            } catch (e: Throwable) {
                Log.d("myDebug", e.message)

            }
            //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "追加アクティビティから戻ってきたIntentを受信")
        when (requestCode) {
            CommonContract.Code.ADD_TASK -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val taskName = data!!.getStringExtra("taskName")
                        if(taskName != null) taskList!!.addAndRefresh(taskName, false)
                    }
                    RESULT_CANCELED -> {
                        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
