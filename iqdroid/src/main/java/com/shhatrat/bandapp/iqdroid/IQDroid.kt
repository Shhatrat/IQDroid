package com.shhatrat.bandapp.iqdroid

import android.content.Context
import android.util.Log
import com.garmin.android.connectiq.ConnectIQ
import com.garmin.android.connectiq.IQApp
import com.garmin.android.connectiq.IQDevice
import com.shhatrat.bandapp.iqdroid.enums.IQError
import com.shhatrat.bandapp.iqdroid.enums.InitResponse
import io.reactivex.*

class IQDroid(
    private val context: Context,
    private val connectionType:ConnectIQ.IQConnectType = ConnectIQ.IQConnectType.WIRELESS,
    private val applictionId: String){
    private val connectIQ: ConnectIQ = ConnectIQ.getInstance(context, connectionType)
    private lateinit var currentSdkState: InitResponse

    val raw by lazy { Raw(connectIQ, applictionId, currentSdkState) }

    fun initConnectIq(): Single<InitResponse>{
        lateinit var response:SingleEmitter<InitResponse>
        val responseSingle = Single.create(SingleOnSubscribe<InitResponse> { emitter -> response = emitter })

        connectIQ.initialize(context, true, object :ConnectIQ.ConnectIQListener{
            override fun onSdkShutDown() {
                currentSdkState = InitResponse.OnInitializeError.OnSdkShutDown
                response.onSuccess(currentSdkState)
            }

            override fun onInitializeError(p0: ConnectIQ.IQSdkErrorStatus?) {
                when(p0){
                    ConnectIQ.IQSdkErrorStatus.GCM_NOT_INSTALLED -> {currentSdkState = InitResponse.OnInitializeError.GCM_NOT_INSTALLED}
                    ConnectIQ.IQSdkErrorStatus.GCM_UPGRADE_NEEDED -> {currentSdkState = InitResponse.OnInitializeError.GCM_UPGRADE_NEEDED}
                    ConnectIQ.IQSdkErrorStatus.SERVICE_ERROR -> {currentSdkState = InitResponse.OnInitializeError.SERVICE_ERROR}
                }
                response.onError(IQError(currentSdkState as InitResponse.OnInitializeError))
            }

            override fun onSdkReady() {
                currentSdkState = InitResponse.OnSdkReady
                response.onSuccess(currentSdkState)
            }
        })
        return responseSingle
    }
}