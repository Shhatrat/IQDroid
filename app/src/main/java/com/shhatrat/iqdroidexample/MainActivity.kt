package com.shhatrat.iqdroidexample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.garmin.android.connectiq.ConnectIQ
import com.garmin.android.connectiq.IQApp
import com.garmin.android.connectiq.IQDevice
import com.shhatrat.iqdroid.IQDroid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var receiveDisposable: Disposable? = null
    lateinit var connectIq: IQDroid
    lateinit var device: IQDevice
    lateinit var app: IQApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectIq = IQDroid(
            this,
            ConnectIQ.IQConnectType.TETHERED,
            "59768915c5f84417bd35f456ef34a888"
        )


        initBtn.setOnClickListener {
            connectIq.initConnectIq().subscribe({
                it.toString().toast()
                device = connectIq.raw.getKnownDevices().first()
                connectIq.raw.getAppInfo(device).subscribe({
                    app = it
                    containerLL.visibility = View.VISIBLE
                }, {
                    it.message?.toast()
                })

            }, {
                it.message?.toast()
            })
        }

        firstDeviceBtn.setOnClickListener {
            val device = connectIq.raw.getKnownDevices().first()
            "${device.friendlyName} -> ${device.status.name}".toast()
        }

        sendAbcBtn.setOnClickListener {
            connectIq.raw.sendMessage(device, app, "ABC")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.name.toast()
                }, {
                    it.message?.toast()
                })
        }

        openApp.setOnClickListener {
            connectIq.raw.openApp(device, app)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.name.toast()
                }, {
                    it.message?.toast()
                })
        }

        receiveSw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (receiveDisposable?.isDisposed == true || receiveDisposable == null)
                    setReceive()
            } else {
                receiveDisposable?.dispose()
            }
        }
    }

    private fun setReceive() {
        receiveDisposable = connectIq.raw.getAppMessages(device, app)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                "${it.first} <--> ${it.second}".toast()
            }, {
                it.message?.toast()
            })
    }

    private fun String.toast() {
        Toast.makeText(this@MainActivity, this, Toast.LENGTH_SHORT).show()
    }
}
