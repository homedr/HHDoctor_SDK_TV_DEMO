package com.hhmedic.app.patient.tv.application

import android.app.Application

class TVApplication:Application() {

    override fun onCreate() {

        super.onCreate()
        TvConfig.initSDK(this)
    }
}