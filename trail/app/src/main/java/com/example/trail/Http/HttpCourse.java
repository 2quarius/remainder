package com.example.trail.Http;

import okhttp3.*;
import java.io.IOException;

public class HttpCourse {
    private final OkHttpClient client_get = new OkHttpClient();

    public void run_get() throws Exception {
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder =HttpUrl.parse(" https://jaccount.sjtu.edu.cn/oauth2/authorize")
                .newBuilder();
        urlBuilder.addQueryParameter("user", "1234");
        urlBuilder.addQueryParameter("password", "1234");
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();
        Response response = client_get.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        System.out.println(response.body().string());
        //写入course
        
    }
}
