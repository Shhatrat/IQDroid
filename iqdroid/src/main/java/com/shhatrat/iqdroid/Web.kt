package com.shhatrat.iqdroid

import com.google.gson.Gson
import com.shhatrat.iqdroid.model.WebBody
import fi.iki.elonen.NanoHTTPD
import java.util.*

class Web(port: Int) : NanoHTTPD(port) {

    private var webpage = ""

    fun setData(data: String){
        webpage = data
    }

    fun setData(data: WebBody){
        webpage = Gson().toJson(data)
    }

    override fun serve(session: IHTTPSession): Response {
        return newFixedLengthResponse(webpage)
    }
}