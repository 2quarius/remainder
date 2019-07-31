package com.example.trail.Http;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendRequestWithOkHttp(Request.Builder address,okhttp3.Callback callback)
    {
        OkHttpClient client=new OkHttpClient();
        Request request=address.build();
        client.newCall(request).enqueue(callback);
    }
}
