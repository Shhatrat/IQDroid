package com.shhatrat.iqdroid.model

import com.garmin.android.connectiq.ConnectIQ

data class DataResponse(
    val battery: Float?,
    val gps: GpsData?,
    val hr: Int?,
    val cadence: Int?,
    val accel: Array<Int>?,
    val altitude: Float?,
    val heading: Float?,
    val mag: ArrayList<Int>?,
    val power: Int?,
    val speed: Float?,
    val temperature: Float?,
    val pressure: Float?,
    val time: Int?,
    val other: String?,
    val screens: HashMap<String,String>?
) {

    companion object {

        private val HELLO_MESSAGE = "hello IQDroid"

        fun parse(pair: Pair<MutableList<Any>, ConnectIQ.IQMessageStatus>): DataResponse? {
            if (pair.second != ConnectIQ.IQMessageStatus.SUCCESS)
                return null
            if(pair.first[0] == HELLO_MESSAGE)
                return null

            with(pair.first[0] as HashMap<*, *>) {
                return DataResponse(
                    get(IQRequestType.BATTERY.name) as Float?,
                    get(IQRequestType.GPS.name)?.let { GpsData(it as HashMap<String, Any>) },
                    get(IQRequestType.HEART_RATE.name) as Int?,
                    get(IQRequestType.CADENCE.name) as Int?,
                    get(IQRequestType.ACCEL.name) as Array<Int>?,
                    get(IQRequestType.ALTITUDE.name) as Float?,
                    get(IQRequestType.HEADING.name) as Float?,
                    get(IQRequestType.MAG.name) as ArrayList<Int>?,
                    get(IQRequestType.POWER.name) as Int?,
                    get(IQRequestType.SPEED.name) as Float?,
                    get(IQRequestType.TEMPERATURE.name) as Float?,
                    get(IQRequestType.PRESSURE.name) as Float?,
                    get(IQRequestType.TIME.name) as Int?,
                    get(IQRequestType.OTHER.name) as String?,
                    getScreenValue(this)
                )
            }
        }

        private fun getScreenValue(hashMap: java.util.HashMap<*, *>): java.util.HashMap<String, String>? {
            val data = hashMap[IQRequestType.SCREENS.name]
            return if(data is HashMap<*, *>){
                data as java.util.HashMap<String, String>?
            }else hashMapOf()
        }
    }
}