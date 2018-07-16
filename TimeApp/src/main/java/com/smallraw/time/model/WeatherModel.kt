package com.smallraw.time.model

import com.alibaba.fastjson.JSON
import com.smallraw.time.App
import com.smallraw.time.entity.Weather
import com.smallraw.time.http.WeatherRequest
import java.lang.Exception

public class WeatherModel() {
    companion object {
        val WeatherNow = "WeatherNow"
    }

    fun getWertherNow(callback: BaseCallback<Weather>) {
        val exception = App.getInstance().getAppExecutors()
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
                    val get = ConfigModel.get(WeatherNow)
                    if (get != "") {
                        val weather = JSON.toJSON(get) as Weather
                        exception.mainThread().execute {
                            callback.onSuccess(weather)
                        }
                    } else {
                        exception.mainThread().execute {
                            callback.onError(Exception())
                        }
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
