package com.example.chibbistest

import android.app.Application
import com.example.chibbistest.di.AppComponent
import com.example.chibbistest.di.AppModule
import com.example.chibbistest.di.DaggerAppComponent

class ChibbisTestApp : Application() {

    lateinit var appComponent: AppComponent;

    companion object {
        lateinit var instance: ChibbisTestApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initComponent()
    }

    private fun initComponent() {
        appComponent =
            DaggerAppComponent
                .builder()
                .build()
    }
}