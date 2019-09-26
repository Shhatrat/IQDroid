package com.shhatrat.iqdroidexample

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.garmin.android.connectiq.ConnectIQ
import com.shhatrat.iqdroid.IQDroid
import kotlinx.android.synthetic.main.activity_init_sdk.*
import org.jetbrains.anko.startActivity


class InitSdkActivity : ListActivity() {

    var id = "bc7cc261ea9846c9b796a2ddffdd4485"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_sdk)

        idEditText.setText(id)
        initList()

        openNextButton.setOnClickListener {
            startActivity<AppActionChooserActivity>()
        }

        okButton.setOnClickListener {
            hideKeyboard()
            getExampleApp().connectIq = IQDroid(applicationContext, getTypeOfConnectType(), id)

            getExampleApp().connectIq.initConnectIq().subscribe({
                addToList(it.toString())
                val device = getConnecIq().raw.getKnownDevices().first()
                addToList("found devices= ${getConnecIq().raw.getKnownDevices().size}")
                getConnecIq().raw.getKnownDevices().forEachIndexed { index, iqDevice ->
                    run {
                        addToList("device $index name= ${iqDevice.friendlyName}")
                        addToList("device $index status= ${iqDevice.status}")
                        addToList("device $index deviceIdentifier= ${iqDevice.deviceIdentifier}")
                    }
                }

                getConnecIq().raw.getAppInfo(device).subscribe({
                    getExampleApp().app = it
                    getExampleApp().device = device
                    addToList("applicationId= ${it.applicationId}")
                    addToList("displayName= ${it.displayName}")
                    addToList("status= ${it.status}")
                    addToList("version= ${it.version()}")
                    openNextButton.visibility = View.VISIBLE
                    okButton.visibility = View.GONE
                }, {
                    addToList("error= ${it.message}")
                    addToList("error= ${it.localizedMessage}")
                })
            }, {
                addToList("error= ${it.message}")
                addToList("error= ${it.localizedMessage}")
            })
        }
    }

    override fun getListView() = logsListView

    fun getTypeOfConnectType(): ConnectIQ.IQConnectType {
        val typeString = intent.getStringExtra("type")
        if (typeString == ConnectIQ.IQConnectType.TETHERED.name)
            return ConnectIQ.IQConnectType.TETHERED
        if (typeString == ConnectIQ.IQConnectType.WIRELESS.name)
            return ConnectIQ.IQConnectType.WIRELESS
        return ConnectIQ.IQConnectType.WIRELESS
    }

    fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
