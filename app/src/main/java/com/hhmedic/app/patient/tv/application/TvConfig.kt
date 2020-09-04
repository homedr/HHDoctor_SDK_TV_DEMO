package com.hhmedic.app.patient.tv.application

import android.content.Context
import android.content.pm.ActivityInfo
import com.hhmedic.android.sdk.HHDoctor
import com.hhmedic.android.sdk.config.HHSDKOptions
import com.hhmedic.app.patient.tv.BuildConfig

class TvConfig {

    companion object {

        private const val productID = "9002"

        const val UserToken = "8DC2FD1D49451309DF7123716BFF20843F0D04F68EA2608F6783B874E4F50EEF"

        fun initSDK(context:Context) {
            val options = HHSDKOptions(productID)
            options.dev = BuildConfig.isDev
            options.isDebug = true
            options.mOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            options.isOpenCamera = false
            options.isOpenEvaluation = false

//            options.mWaterMark = "光大永明视频医生"
//            options.useSampleRate16K_HZ = true
//            options.useSampleRate48K_HZ = true

            HHDoctor.init(context,options)
        }
    }

}