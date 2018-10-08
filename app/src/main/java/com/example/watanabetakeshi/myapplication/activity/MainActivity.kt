package com.example.watanabetakeshi.myapplication.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.watanabetakeshi.myapplication.R
import com.example.watanabetakeshi.myapplication.Tasks
import com.example.watanabetakeshi.myapplication.contract.CommonContract
import android.view.ContextMenu
import android.view.View


class MainActivity() : AppCompatActivity()
{
    data class MenuId(
        val MOVE : Int = 0,
        val DELETE : Int = 1
    );

    // このクラスでのログのタグ
    val LOG_TAG = "MainActivity"
    // タスクを格納するクラスインスタンス
    private var _taskList : Tasks? = null
    // タスクリストのビュークラス
    private var _listView : ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/ コンポーネントにイベントをはる_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        //////////// タスクリスト
        Log.d(LOG_TAG, "タスクリストの初期データの準備")

        val (listView, taskList) = fun():Pair<ListView, Tasks>{
            val listView = findViewById<ListView>(R.id.taskListView)
            val taskList = Tasks(this, listView)
            // listView.emptyView = findViewById(R.id.emptyView)
            listView.setOnItemClickListener { parent, view, position, id ->
                if (taskList.isChecked(position)) {
                    taskList.unChecked(position)
                } else {
                    taskList.checked(position)
                }
            }
            listView.setOnItemLongClickListener { parent, view, position, id ->
                if (taskList.isChecked(position)) {
                    taskList.delete(position)
                    // チェックしている場合は、コンテキストメニューは表示させないため残りの処理を行わない(true)
                    true
                } else {
                    // チェックしていない場合は、コンテキストメニューを表示するため残りの処理を行う(false)
                    false
                }
            }
            return Pair(listView, taskList)
        }()

        registerForContextMenu(listView)

        // 作成したlistViewオブジェクト、taskListオブジェクトをインスタンス変数に。
        _listView = listView
        _taskList = taskList

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
                Log.e("setOnClickListener", e.message)
            }
            //_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menu?.setHeaderTitle("タスク操作メニュー")
        menu?.add(0, MenuId().MOVE, 0, "タスクリストに移動")
        menu?.add(0, MenuId().DELETE, 1, "タスクを削除")
    }

    // 投げたインテントの戻り値を処理する
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(LOG_TAG, "追加アクティビティから戻ってきたIntentを受信")
        when (requestCode) {
            // タスク追加ボタンから
            CommonContract.Code.ADD_TASK -> {
                when (resultCode) {
                    // 追加OKボタン
                    RESULT_OK -> {
                        val taskName = data?.getStringExtra("taskName")
                        if(taskName != null) {
                            _taskList?.addAndRefresh(taskName, false)
                        }
                    }
                // タスク追加ボタンからの追加キャンセルボタン
                    RESULT_CANCELED -> {
                        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
