package com.example.tasks.task2_intentservice_weather

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.tasks.Constants
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Suppress("DEPRECATION")
class WeatherService : IntentService("WeatherService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val lat = intent.getStringExtra("lat")
            val lon = intent.getStringExtra("lon")

            try {
                val urlString = "https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lon&current_weather=true"
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val result = reader.readText()
                reader.close()

                val broadcastIntent = Intent(Constants.ACTION_WEATHER_UPDATE)
                broadcastIntent.putExtra("weather_data", result)
                sendBroadcast(broadcastIntent)

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("WeatherService", "Ошибка запроса: ${e.message}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("WeatherService", "Сервис уничтожен")
    }
}
