package com.smallraw.time.http

import android.util.Log
import com.alibaba.fastjson.JSON
import com.smallraw.time.entity.Weather
import com.smallraw.time.http.response.BaseResponse
import com.smallraw.time.http.response.WeatherResponse
import com.smallraw.time.utils.is2String
import java.net.HttpURLConnection
import java.net.URL

class WeatherRequest {
    companion object {
        @JvmStatic
        fun getWeatherNow(location: String): Weather? {
            try {
                val url = URL("http://140.143.142.72/weather/now?location=$location")

                Log.e("==net request==", url.toURI().toString())
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 12000
                connection.readTimeout = 5000
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val response = is2String(inputStream)
                    Log.e("==net response==", "$response")
                    connection.disconnect()
                    inputStream.close()
                    val baseResponse = JSON.parseObject(response, WeatherResponse::class.java)
                    if (baseResponse != null && baseResponse.error as Int == 0) {
                        return baseResponse.data
                    } else {
                        return null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}