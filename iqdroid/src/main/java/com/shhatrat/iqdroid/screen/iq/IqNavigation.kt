package com.shhatrat.iqdroid.screen.iq

import com.shhatrat.iqdroid.screen.android.Navigation

data class IqNavigation(
    val id: Int,
    val keyCode :Int,
    val transition: Int?){
    constructor(navigation: Navigation)
            : this(navigation.screen.id, navigation.keyCode, navigation.transition)
}