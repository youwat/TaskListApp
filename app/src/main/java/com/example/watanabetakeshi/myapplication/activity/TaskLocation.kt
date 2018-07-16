package com.example.watanabetakeshi.myapplication.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationProvider
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log

class TaskLocation(val activity : AppCompatActivity, val context : Context, val locationManager: LocationManager)
{
    var latitude : Double? = null
    var longtude : Double? = null
    fun locationStart()
    {
        // 権限がない場合付与するようにダイアログを表示
        if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000)
            Log.d("location", "checkSelfPermission false")
            return
        }

        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2000, 50.0f, object : LocationListener {
            // 現在地がアップデートされた時
            override fun onLocationChanged(location: Location?) {
                latitude = location?.getLatitude() // 緯度
                longtude = location?.getLongitude() // 経度
                Log.d("location", "update : (${latitude}, ${longtude}")
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
}