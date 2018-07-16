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
import android.location.Location
import android.location.LocationListener
import android.support.v4.content.ContextCompat
import android.location.LocationManager
import android.location.LocationProvider
import android.provider.Settings


class MainActivity : AppCompatActivity()
{
    var locationManager: LocationManager? = null


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
                Log.d("myDebug", e.message)

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

        locationStart()
    }

    private fun locationStart()
    {
        // LocationManager インスタンス生成
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // 権限がない場合付与するようにダイアログを表示
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000)
            Log.d("location", "checkSelfPermission false")
            return
        }

        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000, 50.0f, object : LocationListener{
            // 現在地がアップデートされた時
            override fun onLocationChanged(location: Location?) {
                gv.latitude = location?.getLatitude() // 緯度
                gv.longtude = location?.getLongitude() // 経度
                Log.d("location", "update : (${gv.latitude}, ${gv.longtude}")
            }

            // ロケーションステータスが変更された
            // TODO: よくわかってないので後で調べる
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                when (status) {
                    LocationProvider.AVAILABLE ->
                        Log.d("location", "LocationProvider.AVAILABLE")
                    LocationProvider.OUT_OF_SERVICE ->
                        Log.d("location", "LocationProvider.OUT_OF_SERVICE")
                    LocationProvider.TEMPORARILY_UNAVAILABLE ->
                        Log.d("location", "LocationProvider.TEMPORARILY_UNAVAILABLE")
                }
            }

            // TODO: なんだっけこれ
            override fun onProviderEnabled(provider: String?) { }
            override fun onProviderDisabled(provider: String?) { }
        })
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
