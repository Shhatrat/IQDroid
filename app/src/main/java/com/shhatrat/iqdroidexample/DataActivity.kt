package com.shhatrat.iqdroidexample

import android.os.Bundle
import com.shhatrat.iqdroid.model.IQRequestType
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
