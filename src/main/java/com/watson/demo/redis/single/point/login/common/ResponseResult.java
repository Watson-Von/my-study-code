package com.watson.demo.redis.single.point.login.common;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

    public static final int STATE_ERROR = -1;
    public static final int STATE_OK = 1;
    private static final long serialVersionUID = 2158690201147047546L;
    private int status;           //返回状态
    private String message;       //返回信息
    private T data;               //返回数据

    public ResponseResult() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ResponseResult(int status, String message, T data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult [status=" + status + ", message=" + message + ", data=" + data + "]";
    }

}
