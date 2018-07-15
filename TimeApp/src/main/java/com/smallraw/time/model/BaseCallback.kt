package com.smallraw.time.model

public interface BaseCallback<T> {
    fun onSuccess(data: T)
    fun onError(e: Exception)
}