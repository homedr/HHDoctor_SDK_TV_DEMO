package com.hhmedic.app.patient.tv

import android.os.Bundle
import android.widget.Button
import com.hhmedic.android.sdk.HHDoctor
import com.hhmedic.android.sdk.listener.HHLoginListener
import com.hhmedic.app.patient.tv.application.TVRoute
import com.hhmedic.app.patient.tv.application.TvConfig

class LoginActivity : BaseActivity() {

    private lateinit var mLoginButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()
    }

    private fun initUI() {
        mLoginButton = findViewById(R.id.button_login)
        mLoginButton.setOnClickListener {
            loginSDK()
        }
    }

    private fun loginSDK() {
        HHDoctor.login(this, TvConfig.UserToken, object : HHLoginListener {
            override fun onSuccess() {
                forwardMain()
            }

            override fun onError(tips: String?) {
                errorTips("登录出错了 ${tips ?: ""}")
            }

        })
    }


    private fun forwardMain() {
        TVRoute.forwardMain(this)
        finish()
    }
}