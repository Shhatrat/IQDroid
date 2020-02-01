package com.shhatrat.iqdroid.screen.iq

import com.google.gson.annotations.SerializedName
import com.shhatrat.iqdroid.screen.android.Screen

data class IqScreen(
    @SerializedName("i")
    val id: Int,
    @SerializedName("n")
    val navigation: List<IqNavigation>,
    @SerializedName("s")
    val screenItemList: List<IqScreenItem>
){
    constructor(screen: Screen)
            : this(screen.id, screen.navigation.toList().map { it.second }.map { IqNavigation(it) }, screen.screenItemList)
}