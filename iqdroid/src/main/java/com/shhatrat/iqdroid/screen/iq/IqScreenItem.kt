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
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }

    enum class ShortName(val shortCode: String) {
        TYPE_TEXT("DT"),
        TYPE_BACKGROUND("BG"),
        TYPE_LINE("LN"),
        TYPE_CIRCLE("CR"),
        TYPE_ECLIPSE("EL"),
        TYPE_RECTANGLE("RT"),
        TYPE_RECTANGLE_ROUNDED("RTR"),
        FONT("f"),
        TEXT("t"),
        JUSTIFICATION("j"),
        X1("x1"),
        Y1("y1"),
        X2("x2"),
        Y2("y2"),
        HEIGHT("h"),
        WIDTH("w"),
        RADIUS("r"),
        A("a"),
        B("b")
    }

    class Text(
        x: Int,
        y: Int,
        color: Int,
        backgroundColor: Int,
        font: Int,
        text: String,
        justification: Int
    ) : IqScreenItem(
        ShortName.TYPE_TEXT.shortCode, x, y, color, backgroundColor,
        mapOf(
            Pair(ShortName.FONT.shortCode, font),
            Pair(ShortName.TEXT.shortCode, text),
            Pair(ShortName.JUSTIFICATION.shortCode, justification)
        )
    )


    class Line(
        override val color: Int,
        override val backgroundColor: Int,
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int
    ) : IqScreenItem(
        ShortName.TYPE_LINE.shortCode, x1, y2, color, backgroundColor,
        mapOf(
            Pair(ShortName.X1.shortCode, x1),
            Pair(ShortName.Y1.shortCode, y1),
            Pair(ShortName.X2.shortCode, x2),
            Pair(ShortName.Y2.shortCode, y2)
        )
    )

    class RectangleRounded(
        x: Int,
        y: Int,
        override val color: Int,
        override val backgroundColor: Int,
        width: Int,
        height: Int,
        radius: Int
    ) : IqScreenItem(
        ShortName.TYPE_RECTANGLE_ROUNDED.shortCode, x, y, color, backgroundColor,
        mapOf(
            Pair(ShortName.WIDTH.shortCode, width),
            Pair(ShortName.HEIGHT.shortCode, height),
            Pair(ShortName.RADIUS.shortCode, radius)
        )
    )

    class Rectangle(
        x: Int,
        y: Int,
        override val color: Int,
        override val backgroundColor: Int,
        width: Int,
        height: Int
    ) : IqScreenItem(
        ShortName.TYPE_RECTANGLE.shortCode, x, y, color, backgroundColor,
        mapOf(Pair(ShortName.WIDTH.shortCode, width), Pair(ShortName.HEIGHT.shortCode, height))
    )

    class Circle(
        x: Int,
        y: Int,
        override val color: Int,
        override val backgroundColor: Int,
        radius: Int
    ) : IqScreenItem(
        ShortName.TYPE_CIRCLE.shortCode, x, y, color, backgroundColor,
        mapOf(Pair(ShortName.RADIUS.shortCode, radius))
    )

    class Eclipse(
        x: Int,
        y: Int,
        override val color: Int,
        override val backgroundColor: Int,
        a: Int,
        b: Int
    ) : IqScreenItem(
        ShortName.TYPE_ECLIPSE.shortCode, x, y, color, backgroundColor,
        mapOf(Pair(ShortName.A.shortCode, a), Pair(ShortName.B.shortCode, b))
    )


    class Background(
        override val color: Int,
        override val backgroundColor: Int
    ) : IqScreenItem(
        ShortName.TYPE_BACKGROUND.shortCode, 0, 0, color, backgroundColor,
        mapOf()
    )
}