package com.smallraw.time.http

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
            val url = URL("http://140.143.142.72/weather/now?location=$location")

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == 200) {
                val inputStream = connection.inputStream
                val response = is2String(inputStream)
                val baseResponse = JSON.parseObject(response, WeatherResponse::class.java)
                if (baseResponse != null && baseResponse.error as Int == 0) {
                    return baseResponse.data
                } else {
                    return null
                }
            }
            return null
        }
    }
}