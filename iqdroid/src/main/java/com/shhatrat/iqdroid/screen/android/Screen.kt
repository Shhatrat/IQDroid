package com.shhatrat.iqdroid.screen.android

import com.shhatrat.iqdroid.screen.iq.IqScreenItem

class Screen private constructor(
    val id: Int,
    val description: String?,
    val localData: Any?,
    val navigation: MutableMap<Int, Navigation>,
    val screenItemList: MutableList<IqScreenItem>
) {

    companion object {
        const val NO_ID = -100
        const val EXIT_ID = -1
        @JvmStatic
        fun getExitScreen(): Screen =
            Screen(EXIT_ID, null, null, mutableMapOf(), mutableListOf())
    }

    data class Builder(
        var description: String? = null,
        var localData: Any? = null,
        var navigation: MutableMap<Int, Navigation>? = null,
        var screenItemList: MutableList<IqScreenItem>? = null
    ) {

        fun description(description: String) = apply { this.description = description }
        fun localData(localData: Any) = apply { this.localData = localData }
        fun navigation(navigation: MutableMap<Int, Navigation>) =
            apply { this.navigation = navigation }

        fun screenItemList(screenItemList: MutableList<IqScreenItem>) =
            apply { this.screenItemList = screenItemList }

        fun build() = Screen(
            NO_ID,
            description,
            localData,
            navigation ?: mutableMapOf(),
            screenItemList ?: mutableListOf()
        )
    }

    fun addId(idToAdd: Int): Screen {
        return Screen(
            idToAdd,
            this.description,
            this.localData,
            this.navigation,
            this.screenItemList
        )
    }
}