package com.shhatrat.iqdroidexample

import android.R
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

abstract class ListActivity : AppCompatActivity() {

    var items = arrayOf<String>()

    abstract fun getListView(): ListView

    fun initList() {
        getListView().adapter = ArrayAdapter<String>(
            applicationContext,
            R.layout.simple_list_item_1,
            items
        )
    }

    fun addToList(item: String) {
        items = items.plus(item)
        initList()
        getListView().setSelection(items.size - 1)
    }
}