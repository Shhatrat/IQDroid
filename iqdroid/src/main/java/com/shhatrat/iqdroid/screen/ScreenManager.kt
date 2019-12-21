package com.shhatrat.iqdroid.screen

import com.shhatrat.iqdroid.screen.android.Navigation
import com.shhatrat.iqdroid.screen.android.Screen
import com.shhatrat.iqdroid.screen.android.Screen.Companion.NO_ID
import com.shhatrat.iqdroid.screen.iq.IqScreen
import com.shhatrat.iqdroid.screen.iq.IqScreenItem

object ScreenManager{

    enum class KEY(internal val keyCode: Int){
        DOWN(8), UP(13), ENTER(3), BACK(4)
    }

    private val screenMap: MutableMap<Int, Screen> = mutableMapOf()

    fun addScreen(screen: Screen): Screen {

        val screenWithId =
            if(screen.id==NO_ID)
                screen.addId(generateNewId())
            else
                screen
        screenMap[screenWithId.id] = screenWithId
        return screenWithId
    }

    fun removeScreen(screenId: Int){
        screenMap.remove(screenId)
    }

    fun addScreenToKey(screen: Screen, screenToKey: Screen, key: KEY){
        val item = screenMap[screen.id]
        item?.navigation?.put(key.keyCode, Navigation(addScreen(screenToKey), key.keyCode))
    }

    fun addExitToKey(screen: Screen, key: KEY){
        val item = screenMap[screen.id]
        item?.navigation?.put(key.keyCode, Navigation(Screen.getExitScreen(), key.keyCode))
    }

    fun removeScreenToKey(screen: Screen, key: KEY){
        val item = screenMap[screen.id]
        item?.navigation?.remove(key.keyCode)
    }

    fun addScreenItem(screen: Screen, screenItem: IqScreenItem){
        val item = screenMap[screen.id]
        item?.screenItemList?.add(screenItem)
    }

    fun removeScreenItem(screen: Screen, screenItem: IqScreenItem){
        val item = screenMap[screen.id]
        item?.screenItemList?.remove(screenItem)
    }

    fun removeScreenItem(screen: Screen, screenItemPosition: Int){
        val item = screenMap[screen.id]
        item?.screenItemList?.removeAt(screenItemPosition)
    }

    private fun generateNewId(): Int = (screenMap.keys.max()?:0)+1

    fun getCurrentJson(): List<IqScreen> = screenMap.map { it.value }.map { IqScreen(it) }
}