package com.shhatrat.iqdroid.screen.iq

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

sealed class IqScreenItem(
    @SerializedName("t")
    open val type: String,
    open val x: Int,
    open val y: Int,
    @SerializedName("c")
    open val color: Int,
    @SerializedName("b")
    open val backgroundColor: Int,
    @SerializedName("d")
    open val data: Map<String, Any>
){
   override fun toString(): String {
            return Gson().toJson(this)
        }

    class Text(x: Int,
               y: Int,
               color: Int,
               backgroundColor: Int,
               font: Int,
               text: String,
               justification: Int)
        : IqScreenItem("DT", x, y, color, backgroundColor,
        mapOf(Pair("f", font),Pair("t", text),Pair("j", justification)))


    class Line(override val color: Int,
               override val backgroundColor: Int,
               x1: Int,
               y1: Int,
               x2: Int,
               y2: Int) : IqScreenItem("LN", x1, y2, color, backgroundColor,
        mapOf(Pair("x1", x1),Pair("y1", y1),Pair("x2", x2), Pair("y2", y2)))
}