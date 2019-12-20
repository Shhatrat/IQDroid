package com.shhatrat.iqdroid.screen.iq

import com.google.gson.annotations.SerializedName
import com.shhatrat.iqdroid.screen.android.Navigation

data class IqNavigation(
    @SerializedName("i")
    val id: Int,
    @SerializedName("k")
    val keyCode :Int,
    @SerializedName("t")
    val transition: Int?){
    constructor(navigation: Navigation)
            : this(navigation.screen.id, navigation.keyCode, navigation.transition)
}