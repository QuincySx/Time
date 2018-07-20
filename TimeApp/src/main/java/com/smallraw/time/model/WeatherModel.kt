package com.smallraw.time.model

import android.support.annotation.DrawableRes
import android.util.Log
import com.alibaba.fastjson.JSON
import com.smallraw.time.App
import com.smallraw.time.R
import com.smallraw.time.entity.Weather
import com.smallraw.time.http.WeatherRequest
import java.lang.Exception

public class WeatherModel() {
    companion object {
        val WeatherNow = "WeatherNow"

        val exception = App.getInstance().getAppExecutors()

        @JvmStatic
        @DrawableRes
        fun getWeatherWhiteImage(state: String): Int {
            val stateCode = state.toInt()
            Log.e("=WeatherState=", state)
            val drawableInt: Int
            when (stateCode) {
                100 -> {
                    drawableInt = R.drawable.ic_weather_qing_white
                }
                101, 102, 103 -> {
                    drawableInt = R.drawable.ic_weather_duoyun_white
                }
                104 -> {
                    drawableInt = R.drawable.ic_weather_duoyun2yin_white
                }
                200, 201, 202, 203, 204, 205, 206, 207, 208 -> {
                    drawableInt = R.drawable.ic_weather_feng_white
                }
                209, 210, 211, 212, 213 -> {
                    drawableInt = R.drawable.ic_weather_taifeng_white
                }
                300, 301, 306, 307, 308, 310, 311, 312, 313, 314, 315, 316, 317, 318, 399 -> {
                    drawableInt = R.drawable.ic_weather_zhongyu_white
                }
                302, 303, 304 -> {
                    drawableInt = R.drawable.ic_weather_leizhenyu_white
                }
                305, 309 -> {
                    drawableInt = R.drawable.ic_weather_xiaoyu_white
                }
                400 -> {
                    drawableInt = R.drawable.ic_weather_xiaoxue_white
                }
                401, 402, 403, 405, 407, 408, 409, 499 -> {
                    drawableInt = R.drawable.ic_weather_zhongxue_white
                }
                404, 406 -> {
                    drawableInt = R.drawable.ic_weather_yujiaxue_white
                }
                500, 501, 509, 510, 514, 515 -> {
                    drawableInt = R.drawable.ic_weather_wu_white
                }
                502, 511, 512, 513 -> {
                    drawableInt = R.drawable.ic_weather_wumai_white
                }
                503, 504 -> {
                    drawableInt = R.drawable.ic_weather_shachen_white
                }
                507, 508 -> {
                    drawableInt = R.drawable.ic_weather_shachenbao_white
                }
                900, 901, 999 -> {
                    drawableInt = R.drawable.ic_weather_qing_white
                }
                else -> {
                    drawableInt = R.drawable.ic_weather_qing_white
                }
            }
            Log.e("=WeatherState=", drawableInt.toString())
            return drawableInt
        }

        @JvmStatic
        @DrawableRes
        fun getWeatherImage(state: String): Int {
            val stateCode = state.toInt()
            Log.e("=WeatherState=", state)
            val drawableInt: Int
            when (stateCode) {
                100 -> {
                    drawableInt = R.drawable.ic_weather_qing
                }
                101, 102, 103 -> {
                    drawableInt = R.drawable.ic_weather_duoyun
                }
                104 -> {
                    drawableInt = R.drawable.ic_weather_duoyun2yin
                }
                200, 201, 202, 203, 204, 205, 206, 207, 208 -> {
                    drawableInt = R.drawable.ic_weather_feng
                }
                209, 210, 211, 212, 213 -> {
                    drawableInt = R.drawable.ic_weather_taifeng
                }
                300, 301, 306, 307, 308, 310, 311, 312, 313, 314, 315, 316, 317, 318, 399 -> {
                    drawableInt = R.drawable.ic_weather_zhongyu
                }
                302, 303, 304 -> {
                    drawableInt = R.drawable.ic_weather_leizhenyu
                }
                305, 309 -> {
                    drawableInt = R.drawable.ic_weather_xiaoyu
                }
                400 -> {
                    drawableInt = R.drawable.ic_weather_xiaoxue
                }
                401, 402, 403, 405, 407, 408, 409, 499 -> {
                    drawableInt = R.drawable.ic_weather_zhongxue
                }
                404, 406 -> {
                    drawableInt = R.drawable.ic_weather_yujiaxue
                }
                500, 501, 509, 510, 514, 515 -> {
                    drawableInt = R.drawable.ic_weather_wu
                }
                502, 511, 512, 513 -> {
                    drawableInt = R.drawable.ic_weather_wumai
                }
                503, 504 -> {
                    drawableInt = R.drawable.ic_weather_shachen
                }
                507, 508 -> {
                    drawableInt = R.drawable.ic_weather_shachenbao
                }
                900, 901, 999 -> {
                    drawableInt = R.drawable.ic_weather_qing
                }
                else -> {
                    drawableInt = R.drawable.ic_weather_qing
                }
            }
            Log.e("=WeatherState=", drawableInt.toString())
            return drawableInt
        }
    }

    fun getWeatherNow(callback: BaseCallback<Weather>) {
        exception.networkIO().execute {
            try {
                val locationModel = LocationModel()
                val currentLocation = locationModel.getCurrentLocation(App.getInstance())

                val weatherNow = WeatherRequest.getWeatherNow(currentLocation)
                if (weatherNow != null) {
                    ConfigModel.set(WeatherNow, JSON.toJSONString(weatherNow), 1000 * 60 * 60 * 24)
                    exception.mainThread().execute {
                        callback.onSuccess(weatherNow)
                    }
                } else {
                    getWeatherCache(callback)
                }
            } catch (e: Exception) {
                exception.mainThread().execute {
                    callback.onError(e)
                }
            }
        }
    }

    fun getWeatherCache(callback: BaseCallback<Weather>) {
        exception.networkIO().execute {
            try {
                val get = ConfigModel.get(WeatherNow, false)
                if (get != "") {
                    val weather = JSON.parseObject(get, Weather::class.java)
                    exception.mainThread().execute {
                        callback.onSuccess(weather)
                    }
                } else {
                    exception.mainThread().execute {
                        callback.onError(Exception())
                    }
                }
            } catch (e: Exception) {
                exception.mainThread().execute {
                    callback.onError(e)
                }
            }
        }
    }
}
