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

    fun getData(device: IQDevice, app: IQApp): Observable<DataResponse> {
        return raw.getAppMessages(device, app)
            .map { DataResponse.parse(it) }
    }
}