package com.smallraw.time.http.response

import com.smallraw.time.entity.Weather

public class WeatherResponse(error: String, message: String, data: Weather) : BaseResponse<Weather>(error, message, data)