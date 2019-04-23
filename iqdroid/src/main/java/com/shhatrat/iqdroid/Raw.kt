package com.shhatrat.iqdroid

import com.garmin.android.connectiq.ConnectIQ
import com.garmin.android.connectiq.IQApp
import com.garmin.android.connectiq.IQDevice
import com.shhatrat.iqdroid.enums.IQError
import com.shhatrat.iqdroid.enums.InitResponse
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single

/**
 * Class contains raw ConnectIQ functions with RxJava2
 */
class Raw(private val connectIQ: ConnectIQ,
          private val applictionId: String,
          private val currentSdkState: InitResponse){

    fun getAppFromFirstDevice()= getKnownDevicesAsRx().flatMap { getAppInfo(it, applictionId,false) }.firstOrError()

    fun getKnownDevices(): List<IQDevice>{
        if(currentSdkState == InitResponse.OnSdkReady)
            return connectIQ.knownDevices
        else
            throw IQError(currentSdkState as InitResponse.OnInitializeError)
    }

    fun getKnownDevicesAsRx(): Observable<IQDevice> {
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

    fun getAppInfo(device: IQDevice, appId: String = applictionId, openIQStore: Boolean = false): Observable<IQApp> {
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

    fun sendMessage(device: IQDevice, iqApp: IQApp, data: String): Single<ConnectIQ.IQMessageStatus> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }

    fun sendMessage(device: IQDevice, iqApp: IQApp, data: Boolean): Single<ConnectIQ.IQMessageStatus> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }

    fun sendMessage(device: IQDevice, iqApp: IQApp, data: Int): Single<ConnectIQ.IQMessageStatus> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }
    @JvmName("sendMessageListString")
    fun sendMessage(device: IQDevice, iqApp: IQApp, data: List<String>): Single<ConnectIQ.IQMessageStatus> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }
    @JvmName("sendMessageListInt")
    fun sendMessage(device: IQDevice, iqApp: IQApp, data: List<Int>): Single<ConnectIQ.IQMessageStatus> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }

    @JvmName("sendMessageListBoolean")
    fun sendMessage(device: IQDevice, iqApp: IQApp, data: List<Boolean>): Single<ConnectIQ.IQMessageStatus> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }

    fun sendMessage(device: IQDevice, iqApp: IQApp, data: Map<Any, Any>): Single<ConnectIQ.IQMessageStatus> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Single.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Single.create{ emitter ->
            connectIQ.sendMessage(device, iqApp, data
            ) { _, _, status -> emitter.onSuccess(status) }
        }
    }

    fun getAppMessages(device: IQDevice, iqApp: IQApp): Observable<Pair<MutableList<Any>, ConnectIQ.IQMessageStatus>> {
        if (currentSdkState != InitResponse.OnSdkReady)
            return Observable.error(IQError(currentSdkState as InitResponse.OnInitializeError))

        return Observable.create(ObservableOnSubscribe<Pair<MutableList<Any>, ConnectIQ.IQMessageStatus>> { emitter ->
            connectIQ.registerForAppEvents(device, iqApp
            ) { _, _, data, status -> emitter.onNext(Pair(data,status)) }
        }).share()
            .doOnDispose { connectIQ.unregisterForApplicationEvents(device, iqApp) }

    }
}