package com.shhatrat.iqdroid.model

data class GpsData(
    val altitude: Float,
    val lng: Double,
    val heading: Float,
    val accuracy: Int,
    val speed: Float,
    val lat: Double,
    val timestamp: Int
    ){
    constructor(map: HashMap<String, Any>) : this(
        map["altitude"] as Float,
        map["lng"] as Double,
        map["heading"] as Float,
        map["accuracy"] as Int,
        map["speed"] as Float,
        map["lat"] as Double,
        map["timestamp"] as Int)
}