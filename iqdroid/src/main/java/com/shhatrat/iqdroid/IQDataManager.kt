package com.shhatrat.iqdroid

import com.shhatrat.iqdroid.model.IQRequestType
import com.shhatrat.iqdroid.model.WebBody
import java.util.*

class IQDataManager(private val web: Web){

    private val setOfIQRequests = mutableSetOf<IQRequestType>()

    fun addType(type: IQRequestType){
        setOfIQRequests.add(type)
        updateWeb()
    }

    fun remove(type: IQRequestType){
        setOfIQRequests.remove(type)
        updateWeb()
    }

    private fun updateWeb(){
        web.setData(WebBody(
            Date().time,
            setOfIQRequests.toList())
        )
    }

}