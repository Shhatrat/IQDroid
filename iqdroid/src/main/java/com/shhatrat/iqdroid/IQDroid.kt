package com.shhatrat.iqdroid

import android.content.Context
import com.garmin.android.connectiq.ConnectIQ
import com.shhatrat.iqdroid.model.IQError
import com.shhatrat.iqdroid.model.InitResponse
import io.reactivex.Single

class IQDroid(
    private val context: Context,
    private val connectionType:ConnectIQ.IQConnectType = ConnectIQ.IQConnectType.WIRELESS,
    private val applictionId: String){
    private val connectIQ: ConnectIQ = ConnectIQ.getInstance(context, connectionType)
    private lateinit var currentSdkState: InitResponse

    val raw by lazy { Raw(connectIQ, applictionId, currentSdkState) }

    fun initConnectIq(): Single<InitResponse>{
        return Single.create {
            connectIQ.initialize(context, true, object : ConnectIQ.ConnectIQListener {
                override fun onSdkShutDown() {
                    currentSdkState = InitResponse.OnInitializeError.OnSdkShutDown
                    it.onSuccess(currentSdkState)
                }
                override fun onInitializeError(p0: ConnectIQ.IQSdkErrorStatus?) {
                    when (p0) {
                        ConnectIQ.IQSdkErrorStatus.GCM_NOT_INSTALLED -> {
                            currentSdkState = InitResponse.OnInitializeError.GCM_NOT_INSTALLED
                        }
                        ConnectIQ.IQSdkErrorStatus.GCM_UPGRADE_NEEDED -> {
                            currentSdkState = InitResponse.OnInitializeError.GCM_UPGRADE_NEEDED
                        }
                        ConnectIQ.IQSdkErrorStatus.SERVICE_ERROR -> {
                            currentSdkState = InitResponse.OnInitializeError.SERVICE_ERROR
                        }
                    }
                    it.onError(IQError(currentSdkState as InitResponse.OnInitializeError))
                }
                override fun onSdkReady() {
                    currentSdkState = InitResponse.OnSdkReady
                    it.onSuccess(currentSdkState)
                }
            })
        }
    }
}