package com.shhatrat.iqdroid.screen.iq

import com.google.gson.Gson

sealed class IqScreenItem(
    open val type: String,
    open val x: Int,
    open val y: Int,
    open val color: Int,
    open val backgroundColor: Int,
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
               justification: Int) : IqScreenItem("DRAW_TEXT", x, y, color, backgroundColor,
        mapOf(Pair("font", font),Pair("text", text),Pair("justification", justification)))


    class Line(override val color: Int,
               override val backgroundColor: Int,
               x1: Int,
               y1: Int,
               x2: Int,
               y2: Int,
               justification: Int) : IqScreenItem("LINE", x1, y2, color, backgroundColor,
        mapOf(Pair("x1", x1),Pair("y1", y1),Pair("x2", x2), Pair("y2", y2)))
}