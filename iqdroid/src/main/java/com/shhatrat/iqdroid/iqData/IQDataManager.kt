package com.shhatrat.iqdroid.iqData

import com.garmin.android.connectiq.IQApp
import com.garmin.android.connectiq.IQDevice
import com.shhatrat.iqdroid.Raw
import com.shhatrat.iqdroid.model.DataResponse
import com.shhatrat.iqdroid.model.IQRequestType
import com.shhatrat.iqdroid.model.WebBody
import io.reactivex.Observable
import java.util.*

class IQDataManager(private val raw: Raw, private val web: Web) {

    private val setOfIQRequests = mutableSetOf<IQRequestType>()

    fun addType(type: IQRequestType){
        setOfIQRequests.add(type)
        updateWeb()
    }

    fun remove(type: IQRequestType){
        setOfIQRequests.remove(type)
        updateWeb()
    }

    private fun updateWeb(){
        web.setData(WebBody(
            Date().time,
            setOfIQRequests.toList())
        )
        if (!web.wasStarted())
            web.start()
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