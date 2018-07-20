package com.smallraw.time.http.response;

/**
 * @author QuincySx
 * @date 2018/7/19 上午10:46
 */
public class BaseResponse<T> {
    public int error;
    public String message;
    public T data;
}
