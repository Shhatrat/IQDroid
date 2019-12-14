package com.shhatrat.iqdroid.model

import com.shhatrat.iqdroid.screen.iq.IqScreen

data class WebBody(
    var id: Long,
    val req: List<IQRequestType>,
    var data: String,
    var screens: List<IqScreen>
)