package com.smallraw.time.http.response

public open class BaseResponse<T : Any>(val error: String, val message: String, val data: T)