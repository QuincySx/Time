package com.smallraw.time.model

import com.smallraw.time.App
import com.smallraw.time.entity.Weather
import com.smallraw.time.http.WeatherRequest
import java.lang.Exception

public class WeatherModel() {
    fun getWertherNow(callback: BaseCallback<Weather>) {
        try {
            val locationModel = LocationModel()
            val currentLocation = locationModel.getCurrentLocation(App.getInstance())

            val weatherNow = WeatherRequest.getWeatherNow(currentLocation)
            if (weatherNow != null) {
                callback.onSuccess(weatherNow)
            } else {
                callback.onError(Exception())
            }
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}
