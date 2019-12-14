package com.shhatrat.iqdroid.screen

import android.util.Log
import com.google.gson.Gson
import com.shhatrat.iqdroid.screen.android.Navigation
import com.shhatrat.iqdroid.screen.android.Screen
import com.shhatrat.iqdroid.screen.android.Screen.Companion.NO_ID
import com.shhatrat.iqdroid.screen.iq.IqScreen
import com.shhatrat.iqdroid.screen.iq.IqScreenItem

object ScreenManager{

    enum class KEY(internal val keyCode: Int){
        DOWN(13), UP(8), ENTER(3), BACK(4)
    }

    private val screenMap: MutableMap<Int, Screen> = mutableMapOf()

    fun addScreen(screen: Screen): Screen {

        val screenWithId =
            if(screen.id==NO_ID)
                screen.addId(generateNewId())
            else
                screen
        screenMap[screenWithId.id] = screenWithId
        handleChanges()
        return screenWithId
    }

    fun removeScreen(screenId: Int){
        screenMap.remove(screenId)
        handleChanges()
    }

    fun addScreenToKey(screen: Screen, screenToKey: Screen, key: KEY, transition: Int?){
        val item = screenMap[screen.id]
        item?.navigation?.put(key.keyCode, Navigation(addScreen(screenToKey), key.keyCode, transition))
        handleChanges()
    }

    fun removeScreenToKey(screen: Screen, key: KEY){
        val item = screenMap[screen.id]
        item?.navigation?.remove(key.keyCode)
        handleChanges()
    }

    fun addScreenItem(screen: Screen, screenItem: IqScreenItem){
        val item = screenMap[screen.id]
        item?.screenItemList?.add(screenItem)
        handleChanges()
    }

    fun removeScreenItem(screen: Screen, screenItem: IqScreenItem){
        val item = screenMap[screen.id]
        item?.screenItemList?.remove(screenItem)
        handleChanges()
    }

    fun removeScreenItem(screen: Screen, screenItemPosition: Int){
        val item = screenMap[screen.id]
        item?.screenItemList?.removeAt(screenItemPosition)
        handleChanges()
    }

    private fun generateNewId(): Int = (screenMap.keys.max()?:0)+1

    private fun handleChanges(){
        val dd=  screenMap.map { it.value }.map { IqScreen(it) }
        Log.d("ScreenManager", Gson().toJson(dd))
    }

    fun getCurrentJson(): List<IqScreen> = screenMap.map { it.value }.map { IqScreen(it) }
}