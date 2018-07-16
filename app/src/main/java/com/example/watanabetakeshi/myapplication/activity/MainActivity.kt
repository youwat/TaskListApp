package com.example.watanabetakeshi.myapplication.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.watanabetakeshi.myapplication.R
import com.example.watanabetakeshi.myapplication.Tasks
import com.example.watanabetakeshi.myapplication.contract.CommonContract
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.location.LocationManager


class MainActivity : AppCompatActivity()
{
    var locationManager: LocationManager? = null
    var location : TaskLocation? = null


    val LOG_TAG = "MainActivity"

    private var _taskList : Tasks? = null
    private var _listView : ListView? = null
    private val REQUEST_CODE = 1
    private val gv : GlobalVariable = GlobalVariable()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(LOG_TAG, "ok")
                } else {
                    Log.d(LOG_TAG, grantResults.toString())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //_/_/_/_/_/_/_/_/_/_/_/_/_/_/ コンポーネントにイベントをはる_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        //////////// タスクリスト
        Log.d(LOG_TAG, "タスクリストの初期データの準備")
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
            } else {
                taskList.unChecked(position)
            }
            true
        }
        this._listView = listView
        this._taskList = taskList

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

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
            ), REQUEST_CODE)
            return
        }

        // Location関係
        location = TaskLocation(this, this, getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        location?.locationStart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(LOG_TAG, "追加アクティビティから戻ってきたIntentを受信")
        when (requestCode) {
            CommonContract.Code.ADD_TASK -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val taskName = data!!.getStringExtra("taskName")
                        if(taskName != null) _taskList!!.addAndRefresh(taskName, false)
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
