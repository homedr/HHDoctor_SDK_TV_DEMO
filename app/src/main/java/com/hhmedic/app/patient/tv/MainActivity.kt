package com.hhmedic.app.patient.tv

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import com.hhmedic.android.sdk.HHDoctor
import com.hhmedic.android.sdk.listener.HHCallListener
import com.hhmedic.android.sdk.module.call.CallType
import com.hhmedic.app.patient.tv.application.TVRoute
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Rationale
import com.yanzhenjie.permission.RequestExecutor

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        findViewById<Button>(R.id.call_adult).setOnClickListener {
            startCall(CallType.all)
        }

        findViewById<Button>(R.id.call_child).setOnClickListener {
            startCall(CallType.child)
        }

        findViewById<Button>(R.id.logout).setOnClickListener {
            logout()
        }
    }

    private fun startCall(type: CallType) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            call(type)
        } else {
            AndPermission.with(this).permission(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            )
                    .rationale(mRationale)
                    .onGranted { _ -> call(type) }

                    .onDenied { permissions ->

                        if (AndPermission.hasAlwaysDeniedPermission(this, permissions)) {
                            // 这些权限被用户总是拒绝。
                            alwaysTips(videoPermissionTips())
                        } else {
                            errorTips(videoPermissionTips())
                        }

                    }
                    .start()
        }

    }

    private fun videoPermissionTips(): String {
        return getString(R.string.hp_call_permission_tips)
    }

    private fun alwaysTips(message: String) {
        val settingService = AndPermission.permissionSetting(this)

        QMUIDialog.MessageDialogBuilder(this).setMessage(message)

            .addAction(R.string.hh_cancel) { dialog, _ ->

                dialog.dismiss()

                settingService.cancel()

            }.addAction("设置") { dialog, _ ->


                dialog.dismiss()

                settingService.execute()


            }.show()
    }

    private val mRationale = Rationale { _: Context, _: MutableList<String>, requestExecutor: RequestExecutor ->
        QMUIDialog.MessageDialogBuilder(this).setMessage(getString(R.string.hp_call_permission_tips)).addAction(
            getString(R.string.hh_cancel)
        ) { dialog, _ ->
            dialog.dismiss()
            requestExecutor.cancel()
        }.addAction(
            "设置"
        ) { dialog, _ ->
            dialog.dismiss()
            requestExecutor.execute()
        }.show()
    }

    private fun call(type: CallType) {
        class CallListener : HHCallListener {

            override fun onStart(p0: String?) {
            }

            override fun onFinish() {
            }

            override fun onCalling() {
            }

            override fun onInTheCall() {
            }

            override fun onFail(p0: Int) {
            }

            override fun onLineUp() {
            }

            override fun onCancel() {
            }

            override fun onCallSuccess() {
            }

            override fun onLineUpTimeout() {
            }

        }
        when (type) {
            CallType.all -> HHDoctor.callForAdult(this, CallListener())
            CallType.child -> HHDoctor.callForChild(this, CallListener())
        }
    }

    private fun logout() {
        HHDoctor.logOut(this)
        finish()
        TVRoute.forwardLogin(this)
    }
}
