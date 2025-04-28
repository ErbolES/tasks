package com.example.tasks.task3_broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.tasks.Constants

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Constants.ACTION_WEATHER_UPDATE) {
            val data = intent.getStringExtra("weather_data")
            Toast.makeText(context, "Погода: $data", Toast.LENGTH_LONG).show()
        }
    }
}