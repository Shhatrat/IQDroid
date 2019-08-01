package com.shhatrat.iqdroid.model

import com.garmin.android.connectiq.ConnectIQ

data class DataResponse(
    val battery: Float?,
    val gps: GpsData?,
    val hr: Int?,
    val cadence: Int?
) {

    companion object {
        fun parse(pair: Pair<MutableList<Any>, ConnectIQ.IQMessageStatus>): DataResponse? {
            if (pair.second != ConnectIQ.IQMessageStatus.SUCCESS)
                return null

            with(pair.first[0] as HashMap<*, *>) {
                return DataResponse(
                    get(IQRequestType.BATTERY.name) as Float?,
                    get(IQRequestType.GPS.name)?.let { GpsData(it as HashMap<String, Any>) },
                    get(IQRequestType.HEART_RATE.name) as Int?,
                    get(IQRequestType.CADENCE.name) as Int?
                )
            }
        }
    }
}