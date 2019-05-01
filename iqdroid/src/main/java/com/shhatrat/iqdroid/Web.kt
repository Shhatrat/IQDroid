package com.shhatrat.iqdroid

import com.google.gson.Gson
import com.shhatrat.iqdroid.model.WebBody
import fi.iki.elonen.NanoHTTPD

class Web(port: Int) : NanoHTTPD(port) {

    private var webpage = ""
    private var lastId = 0L

    fun setData(data: String){
        webpage = data
    }

    fun setData(data: WebBody){
        lastId += 1
        data.id = lastId
        webpage = Gson().toJson(data)
    }

    override fun serve(session: IHTTPSession): Response {
        return newFixedLengthResponse(webpage)
    }
}