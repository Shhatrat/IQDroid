package com.shhatrat.iqdroidexample

import android.os.Bundle
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_raw.*

class RawActivity : ListActivity() {

    var gettingMessages: Disposable? = null
    var gettingStatus: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raw)
        setupListeners()
    }

    override fun getListView() = listView

    fun setupListeners() {
        openAppButton.setOnClickListener { openApp() }
        messagesSwitch.setOnCheckedChangeListener { _, isChecked -> message(isChecked) }
        sendTextButton.setOnClickListener { sendText() }
        statusSwitch.setOnCheckedChangeListener { _, isChecked -> status(isChecked) }
    }

    private fun sendText() {
        getConnecIq().raw.sendMessage(
            getExampleApp().device,
            getExampleApp().app,
            sendTextEditText.text.toString()
        )
            .subscribe({
                addToList(it.name)
            }, {
                it.message?.let { it1 -> addToList(it1) }
            })
    }

    private fun status(checked: Boolean) {
        if (checked) {
            gettingStatus?.dispose()
            gettingStatus = getConnecIq().raw.getStatusOfDevice(getExampleApp().device)
                .subscribe({
                    addToList(it.name)
                }, {
                    it.message?.let { it1 -> addToList(it1) }
                })
        } else
            gettingStatus?.dispose()
    }

    private fun message(checked: Boolean) {
        if (checked) {
            gettingMessages?.dispose()
            gettingMessages =
                getConnecIq().raw.getAppMessages(getExampleApp().device, getExampleApp().app)
                    .subscribe({
                        addToList(it.first.toString())
                        addToList(it.second.name)
                    }, {
                        it.message?.let { it1 -> addToList(it1) }
                    })
        } else
            gettingMessages?.dispose()
    }

    fun openApp() {
        getConnecIq().raw.openApp(getExampleApp().device, getExampleApp().app)
            .subscribe({
                addToList(it.name)
            }, {
                it.message?.let { it1 -> addToList(it1) }
            })
    }
}
