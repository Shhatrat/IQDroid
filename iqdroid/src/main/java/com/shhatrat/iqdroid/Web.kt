package com.shhatrat.iqdroid

import fi.iki.elonen.NanoHTTPD

class Web(port: Int) : NanoHTTPD(port) {

    private var webpage =""

    fun setData(data: String){
        webpage = data
    }

    override fun serve(session: IHTTPSession): Response {
        return newFixedLengthResponse(webpage)
    }
}