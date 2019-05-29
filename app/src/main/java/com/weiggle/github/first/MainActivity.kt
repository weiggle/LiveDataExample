package com.weiggle.github.first

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.ViewModelProviders.of as of1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        liveDataExampleButton.setOnClickListener { startActivity(LiveDataExampleActivity.getLiveData(this)) }

        liveDataMapExampleButton.setOnClickListener { startActivity(LiveDataMapExampleActivity.getMapLiveData(this)) }

        liveDataSwitchMapExampleButton.setOnClickListener { startActivity(LiveDataSwitchMapExampleActivity.getSwitchMapLiveData(this)) }

    }
}
