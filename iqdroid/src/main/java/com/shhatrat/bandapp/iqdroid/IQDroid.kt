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

    private var connectIQ: ConnectIQ = ConnectIQ.getInstance(context, connectionType)
    private lateinit var currentSdkState: InitResponse

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

    fun getKnownDevices(): List<IQDevice>{
        if(currentSdkState == InitResponse.OnSdkReady)
            return connectIQ.knownDevices
        else
            throw IQError(currentSdkState as InitResponse.OnInitializeError)
    }

    fun getKnownDevicesAsRx(): Observable<IQDevice>{
        if(currentSdkState == InitResponse.OnSdkReady)
            return Observable.fromIterable(connectIQ.knownDevices)
        else
            return Observable.error(IQError(currentSdkState as InitResponse.OnInitializeError))
    }

    fun getConnectedDevices() = getKnownDevices().filter { it.status == IQDevice.IQDeviceStatus.CONNECTED }

    fun getConnectedDevicesAsRx() = getKnownDevicesAsRx().filter { it.status == IQDevice.IQDeviceStatus.CONNECTED }

    fun getStatusOfDevice(device: IQDevice): Observable<IQDevice.IQDeviceStatus> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Observable.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Observable.create(ObservableOnSubscribe<IQDevice.IQDeviceStatus> { emitter ->
            connectIQ.registerForDeviceEvents(device) { _, event ->
                emitter.onNext(event)
            }
        }).share()
            .doOnDispose { connectIQ.unregisterForDeviceEvents(device) }
    }

    fun getAppInfo(device: IQDevice, appId: String = applictionId,   openIQStore: Boolean = false):Observable<IQApp> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Observable.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Observable.create(ObservableOnSubscribe<IQApp>{ emitter ->
            connectIQ.getApplicationInfo(appId, device, object : ConnectIQ.IQApplicationInfoListener{
                override fun onApplicationInfoReceived(app: IQApp) {
                    emitter.onNext(app)
                }

                override fun onApplicationNotInstalled(p0: String?) {
                    emitter.onError(IQError(InitResponse.OnInitializeError.OnApplicationNotInstalled))
                    if(openIQStore)
                        connectIQ.openStore(appId)
                }
            })
        }).share()
    }

    fun sendMessage(device: IQDevice, iqApp: IQApp, data: String): Single<ConnectIQ.IQMessageStatus>{
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }

    fun sendMessage(device: IQDevice, iqApp: IQApp, data: Boolean): Single<ConnectIQ.IQMessageStatus>{
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }

    fun sendMessage(device: IQDevice, iqApp: IQApp, data: Int): Single<ConnectIQ.IQMessageStatus>{
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }
    @JvmName("sendMessageListString")
    fun sendMessage(device: IQDevice, iqApp: IQApp, data: List<String>): Single<ConnectIQ.IQMessageStatus>{
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }
    @JvmName("sendMessageListInt")
    fun sendMessage(device: IQDevice, iqApp: IQApp,  data: List<Int>): Single<ConnectIQ.IQMessageStatus>{
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }

    @JvmName("sendMessageListBoolean")
    fun sendMessage(device: IQDevice, iqApp: IQApp,  data: List<Boolean>): Single<ConnectIQ.IQMessageStatus>{
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }

    fun getAppMessages(device: IQDevice, iqApp: IQApp): Observable<Pair<MutableList<Any>, ConnectIQ.IQMessageStatus>>? {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Observable.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Observable.create(ObservableOnSubscribe<Pair<MutableList<Any>, ConnectIQ.IQMessageStatus>> { emitter ->
            connectIQ.registerForAppEvents(device, iqApp
            ) { _, _, data, status -> emitter.onNext(Pair(data,status)) }
        }).share()
            .doOnDispose { connectIQ.unregisterForApplicationEvents(device, iqApp) }

    }
}