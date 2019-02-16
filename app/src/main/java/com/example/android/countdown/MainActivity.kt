package com.example.android.countdown

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val ERIKA_ARRIVAL_CALENDAR = Calendar.getInstance()
    private val CURRENT_TIME = Calendar.getInstance()
    private var timeUntilArrival : Long = 0
    private var countDownTimer: CountDownTimer? = null

    private val ONE_SECOND : Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeTime()
    }

    fun initializeTime() {
        //Set ERIKA_ARRIVAL_CALENDAR to correct date
        ERIKA_ARRIVAL_CALENDAR.set(2019, 2, 4, 3, 30)
        //Get Millis
        val arrivalTimeInMillis = ERIKA_ARRIVAL_CALENDAR.timeInMillis
        val currentTimeInMillis = CURRENT_TIME.timeInMillis

        //Initialize timeUntilArrival
        timeUntilArrival = arrivalTimeInMillis - currentTimeInMillis

        val isHere = (timeUntilArrival < 0)

        if (isHere) {
            countdown.setText(R.string.erika_is_here)
        }
        else {
            countDownTimer = object : CountDownTimer(timeUntilArrival, ONE_SECOND) {
                override fun onFinish() {
                    countdown.setText(R.string.erika_is_here)
                }

                override fun onTick(millisUntilFinished: Long) {
                    updateTime()
                    timeUntilArrival -= ONE_SECOND
                }
            }.start()
        }
    }

    fun updateTime() {
        val seconds = (timeUntilArrival / 1000) % 60
        val minutes = (timeUntilArrival / (1000 * 60)) % 60
        val hours = (timeUntilArrival / (1000 * 60 * 60)) % 24
        val days = (timeUntilArrival / (1000 * 60 * 60 * 24))

        countdown.setText("$days days,\n $hours hours,\n $minutes minutes,\n $seconds seconds")


    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) countDownTimer!!.cancel()
    }

}
