package com.hhmedic.app.patient.tv.application

import android.content.Context
import android.content.Intent
import com.hhmedic.app.patient.tv.LoginActivity
import com.hhmedic.app.patient.tv.MainActivity

class TVRoute {

    companion object {

        fun forwardLogin(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

        fun forwardMain(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}