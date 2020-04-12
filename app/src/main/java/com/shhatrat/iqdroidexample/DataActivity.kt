package com.shhatrat.iqdroidexample

import android.os.Bundle
import com.shhatrat.iqdroid.model.IQRequestType
import com.shhatrat.iqdroid.screen.ScreenManager
import com.shhatrat.iqdroid.screen.android.Screen
import com.shhatrat.iqdroid.screen.iq.IqScreenItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_data.*

class DataActivity : ListActivity() {

    var data: Disposable? = null
    var dataWithStatus: Disposable? = null

    override fun getListView() = loglistView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        setListeners()
    }

    fun changeState(item: IQRequestType, checked: Boolean) {
        if (checked)
            getExampleApp().connectIq.iqDataManager.addType(item)
        else
            getExampleApp().connectIq.iqDataManager.remove(item)
    }

    fun changeScreensState(checked: Boolean){
        if(checked)
            addScreens()
        else
            ScreenManager.removeAllScreens()
    }

    private fun addScreens(){
        val screen1 = Screen.Builder().description("TEST 1").build()
        val screen2 = Screen.Builder().description("TEST 2").build()
        val screen1Id = ScreenManager.addScreen(screen1)
        val screen2Id = ScreenManager.addScreen(screen2)
        ScreenManager.addExitToKey(screen1Id, ScreenManager.KEY.DOWN)
        ScreenManager.addScreenToKey(screen1Id, screen2Id, ScreenManager.KEY.UP)
        ScreenManager.addScreenToKey(screen2Id, screen1Id, ScreenManager.KEY.DOWN)
        ScreenManager.addScreenToKey(screen2Id, screen1Id, ScreenManager.KEY.UP)
        ScreenManager.addScreenItem(screen1Id, IqScreenItem.Text(60, 60, 16711680, 16776960, 4, "text1", 1))
        ScreenManager.addScreenItem(screen1Id, IqScreenItem.Text(120, 120, 16711680, 16776960, 3, "text2", 1))
        ScreenManager.addScreenItem(screen2Id, IqScreenItem.Text(80, 80, 16711680, 16776960, 3, "text3", 1))
        getExampleApp().connectIq.iqDataManager.addIQScreens(ScreenManager.getCurrentJson())
    }


    fun setListeners() {
        gpsSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.GPS,
                checked
            )
        }
        batterySwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.BATTERY,
                checked
            )
        }
        hrSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.HEART_RATE,
                checked
            )
        }
        cadenceSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.CADENCE,
                checked
            )
        }
        accelSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.ACCEL,
                checked
            )
        }
        altitudeSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.ALTITUDE,
                checked
            )
        }
        headingSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.HEADING,
                checked
            )
        }
        magSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.MAG,
                checked
            )
        }
        powerSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.POWER,
                checked
            )
        }
        speedSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.SPEED,
                checked
            )
        }
        temperatureSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.TEMPERATURE,
                checked
            )
        }
        otherSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.OTHER,
                checked
            )
        }

        timeSwitch.setOnCheckedChangeListener { _, checked ->
            changeState(
                IQRequestType.TIME,
                checked
            )
        }
        screenSwitch.setOnCheckedChangeListener { _, checked ->
            changeScreensState(
                checked
            )
        }

        dataSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                data?.dispose()
                data = getExampleApp().connectIq.iqDataManager.getData(
                    getExampleApp().device,
                    getExampleApp().app
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        addToList(it.toString())
                    }, { addToList(it.localizedMessage) })
            } else {
                data?.dispose()
            }
        }

        dataWithStatusSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                dataWithStatus?.dispose()
                dataWithStatus = getExampleApp().connectIq.iqDataManager.getDataWithConnectionState(
                    getExampleApp().device,
                    getExampleApp().app
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        addToList(it.toString())
                    }, { addToList(it.localizedMessage) })
            } else {
                dataWithStatus?.dispose()
            }
        }
    }
}
