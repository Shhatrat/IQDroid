package com.shhatrat.iqdroid.screen.iq

import com.shhatrat.iqdroid.screen.android.Navigation
import com.shhatrat.iqdroid.screen.android.Screen

data class IqScreen(
    val id: Int,
    val navigation: List<IqNavigation>,
    val screenItemList: List<IqScreenItem>
){
    constructor(screen: Screen)
            : this(screen.id, screen.navigation.toList().map { it.second }.map { IqNavigation(it) }, screen.screenItemList)
}