package com.shhatrat.iqdroidexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_app_action_chooser.*
import org.jetbrains.anko.startActivity

class AppActionChooserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_action_chooser)
        rawButton.setOnClickListener {
            startActivity<RawActivity>()
        }
        dataButton.setOnClickListener {
            startActivity<DataActivity>()
        }
    }
}
