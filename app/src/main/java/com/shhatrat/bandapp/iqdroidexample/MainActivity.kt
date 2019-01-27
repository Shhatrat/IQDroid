package com.shhatrat.bandapp.iqdroidexample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shhatrat.bandapp.iqdroid.IQDroid

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectIq = IQDroid(this)

        connectIq.initConnectIq().subscribe({
            Log.d("log", it.toString())

//            val ddd =connectIq.getConnectedDevices()
//            ddd.first().friendlyName

//            connectIq.getStatusOfDevice(connectIq.getConnectedDevices().first())
//                .subscribe({
//                    it.name
//                },{})

        },{})
    }
}
