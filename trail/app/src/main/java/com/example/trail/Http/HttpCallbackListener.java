package com.example.trail.Http;

//回调监听接口类
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
