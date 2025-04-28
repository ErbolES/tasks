package com.example.tasks

import android.annotation.SuppressLint
import android.content.*
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.tasks.task1_service.MyService
import com.example.tasks.task2_intentservice_weather.WeatherService
import com.example.tasks.task3_broadcast_receiver.MyBroadcastReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var receiver: MyBroadcastReceiver

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)

        findViewById<Button>(R.id.startServiceButton).setOnClickListener {
            startService(Intent(this, MyService::class.java))
        }

        findViewById<Button>(R.id.stopServiceButton).setOnClickListener {
            stopService(Intent(this, MyService::class.java))
        }

        findViewById<Button>(R.id.weatherServiceButton).setOnClickListener {
            val intent = Intent(this, WeatherService::class.java)
            intent.putExtra("lat", "35.0")
            intent.putExtra("lon", "139.0")
            startService(intent)
        }

        findViewById<Button>(R.id.longOperationButton).setOnClickListener {
            startLongOperation()
        }

        receiver = MyBroadcastReceiver()
        val filter = IntentFilter().apply {
            addAction(Constants.ACTION_WEATHER_UPDATE)
        }
        registerReceiver(receiver, filter)
    }

    private fun startLongOperation() {
        progressBar.visibility = View.VISIBLE
        Thread {
            Thread.sleep(3000)
            runOnUiThread {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Долгая операция завершена", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}