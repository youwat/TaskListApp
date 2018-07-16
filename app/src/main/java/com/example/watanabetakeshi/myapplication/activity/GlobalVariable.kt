package com.example.watanabetakeshi.myapplication.activity

import android.app.Application

public class GlobalVariable : Application()
{
    var latitude : Double? = null
    var longtude : Double? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}