package com.shhatrat.iqdroidexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.garmin.android.connectiq.ConnectIQ
import kotlinx.android.synthetic.main.activity_connection_type.*
import org.jetbrains.anko.startActivity

class ConnectionTypeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection_type)

        tetheredButton.setOnClickListener { openStartScreen(ConnectIQ.IQConnectType.TETHERED) }
        wirelessButton.setOnClickListener { openStartScreen(ConnectIQ.IQConnectType.WIRELESS) }
    }

    private fun openStartScreen(type: ConnectIQ.IQConnectType) {
        finish()
        startActivity<InitSdkActivity>("type" to type.name)
    }
}
