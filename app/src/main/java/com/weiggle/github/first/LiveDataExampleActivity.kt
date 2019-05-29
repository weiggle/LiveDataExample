package com.weiggle.github.first

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_live_data_example.*
import java.util.*
import java.util.concurrent.TimeUnit

class LiveDataExampleActivity : AppCompatActivity() {

    companion object {
        fun getLiveData(callingClassContext: Context) = Intent(callingClassContext, LiveDataExampleActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_example)

        val timerChangerViewModel = ViewModelProviders.of(this).get(TimerChangerViewModel::class.java)
        val calendar = Calendar.getInstance()
        timerChangerViewModel.timerValue.observe(this, Observer<Long> {
            calendar.timeInMillis = it
            text.text= calendar.time.toString()
        })


    }
}


class TimerChangerViewModel : ViewModel() {
    val timerValue = MutableLiveData<Long>()

    init {
        timerValue.value = System.currentTimeMillis()
        startTimer()
    }

    @SuppressLint("CheckResult")
    private fun startTimer() {
        Observable.interval(2, 2, TimeUnit.SECONDS)
            .subscribe({
                timerValue.postValue(System.currentTimeMillis())
            }, Throwable::printStackTrace)
    }
}