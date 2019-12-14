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
    private var other = ""

    fun addType(type: IQRequestType) {
        setOfIQRequests.add(type)
        updateWeb()
    }

    fun addOther(data: String) {
        other = data
        updateWeb()
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
                getScreens()
            )
        )
        if (!web.wasStarted())
            web.start()
    }

    var screensEnable = false

    fun showScreens(enable: Boolean) {
        screensEnable = enable
    }

    fun getScreens(): List<IqScreen> {
        if (screensEnable) {
            addScreens()
        }
        return ScreenManager.getCurrentJson()
    }

    private fun addScreens(){
        val screen1 = Screen.Builder().description("TEST 1").build()
        val screen2 = Screen.Builder().description("TEST 2").build()
        val screen1Id = ScreenManager.addScreen(screen1)
        val screen2Id = ScreenManager.addScreen(screen2)
        repeat(40){
            val sss = ScreenManager.addScreen(Screen.Builder().description("TEST $it").build())
            ScreenManager.addScreenItem(sss, IqScreenItem.Text(60, 60, 16711680, 16776960, 4, "ho ho ho", 1))
        }
        ScreenManager.addScreenToKey(screen1Id, screen2Id, ScreenManager.KEY.DOWN, 1)
        ScreenManager.addScreenToKey(screen1Id, screen2Id, ScreenManager.KEY.UP, 1)
        ScreenManager.addScreenToKey(screen2Id, screen1Id, ScreenManager.KEY.DOWN, 1)
        ScreenManager.addScreenToKey(screen2Id, screen1Id, ScreenManager.KEY.UP, 1)
        ScreenManager.addScreenItem(screen1Id, IqScreenItem.Text(60, 60, 16711680, 16776960, 4, "ho ho ho", 1))
        ScreenManager.addScreenItem(screen1Id, IqScreenItem.Text(120, 120, 16711680, 16776960, 3, "ppp", 1))
        ScreenManager.addScreenItem(screen2Id, IqScreenItem.Text(80, 80, 16711680, 16776960, 3, "inne 2234", 1))
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