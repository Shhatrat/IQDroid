package com.shhatrat.iqdroid.iqData

import com.garmin.android.connectiq.IQApp
import com.garmin.android.connectiq.IQDevice
import com.shhatrat.iqdroid.Raw
import com.shhatrat.iqdroid.model.DataResponse
import com.shhatrat.iqdroid.model.IQRequestType
import com.shhatrat.iqdroid.model.WebBody
import com.shhatrat.iqdroid.screen.ScreenManager
import com.shhatrat.iqdroid.screen.android.Screen
import com.shhatrat.iqdroid.screen.iq.IqScreen
import com.shhatrat.iqdroid.screen.iq.IqScreenItem
import io.reactivex.Observable
import java.util.*

class IQDataManager(private val raw: Raw, private val web: Web) {

    private val setOfIQRequests = mutableSetOf<IQRequestType>()
    private val listOfIQScreens = mutableListOf<IqScreen>()
    private var other = ""

    fun addType(type: IQRequestType) {
        setOfIQRequests.add(type)
        updateWeb()
    }

    fun addOther(data: String) {
        other = data
        updateWeb()
    }

    fun addIQScreens(items: List<IqScreen>){
        listOfIQScreens.clear()
        items.forEach { listOfIQScreens.add(it) }
    }

    fun remove(type: IQRequestType) {
        setOfIQRequests.remove(type)
        updateWeb()
    }

    private fun updateWeb() {
        web.setData(
            WebBody(
                Date().time,
                setOfIQRequests.toList(),
                other,
                listOfIQScreens
            )
        )
        if (!web.wasStarted())
            web.start()
    }

    var screensEnable = false

    fun showScreens(enable: Boolean) {
        screensEnable = enable
    }

    fun getDataWithConnectionState(device: IQDevice, app: IQApp): Observable<DataResponse> {

        raw.getAppMessages(device, app).map { DataResponse.parse(it) }.firstElement().subscribe()

        val dataObs: Observable<DataResponse> =
            raw.getAppMessages(device, app).map { DataResponse.parse(it) }
        val statusObs: Observable<IQDevice.IQDeviceStatus> = raw.getStatusOfDevice(device)
            .mergeWith(Observable.just(IQDevice.IQDeviceStatus.CONNECTED))

        val list = listOf(dataObs, statusObs)

        return Observable
            .combineLatest(list) { it }
            .switchMap {
                val status = it[1] as IQDevice.IQDeviceStatus
                val data = it[0] as DataResponse
                if (status == IQDevice.IQDeviceStatus.NOT_CONNECTED)
                    Observable.error<DataResponse>(Exception(IQDevice.IQDeviceStatus.NOT_CONNECTED.name))
                else
                    Observable.just(data)
            }
    }


    fun getData(device: IQDevice, app: IQApp): Observable<DataResponse> {
        return raw.getAppMessages(device, app)
            .map { DataResponse.parse(it) }
    }
}