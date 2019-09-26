package com.shhatrat.iqdroidexample

import android.app.Activity
import android.app.Application
import com.garmin.android.connectiq.IQApp
import com.garmin.android.connectiq.IQDevice
import com.shhatrat.iqdroid.IQDroid

class ExampleApp : Application() {

    //use DI!!!
    lateinit var connectIq: IQDroid
    lateinit var app: IQApp
    lateinit var device: IQDevice
}

fun Activity.getExampleApp() = (this.application as ExampleApp)
fun Activity.getConnecIq() = this.getExampleApp().connectIq