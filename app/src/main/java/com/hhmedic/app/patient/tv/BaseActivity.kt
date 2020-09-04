package com.hhmedic.app.patient.tv

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
    }

    fun initActionBar(toolbar: Toolbar?) {
        if (toolbar == null) {
            return
        }
        setSupportActionBar(toolbar)
        initActionBar()
    }

    private fun initActionBar() {
        if (supportActionBar == null) {
            return
        }

        // 以下代码用于去除阴影
        if (Build.VERSION.SDK_INT >= 21) {
            supportActionBar?.elevation = 0f
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onPause() {
        super.onPause()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    protected fun errorTips(tips: String) {
        val toast = Toast.makeText(this, tips, Toast.LENGTH_SHORT)

        toast.setGravity(Gravity.CENTER, 0, 0)

        toast.show()
    }

    protected fun errorTips(tips: Int) {
        val toast = Toast.makeText(this, tips, Toast.LENGTH_SHORT)

        toast.setGravity(Gravity.CENTER, 0, 0)

        toast.show()
    }

    protected fun successTips(tips: String) {
        val toast = Toast.makeText(this, tips, Toast.LENGTH_SHORT)

        toast.setGravity(Gravity.CENTER, 0, 0)

        toast.show()
    }
}