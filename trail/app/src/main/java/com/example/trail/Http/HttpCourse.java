package com.example.trail.Http;

import com.example.trail.Course.Schedule;
import com.google.gson.Gson;

import okhttp3.*;
import java.io.IOException;

public class HttpCourse {
    private final OkHttpClient client_get = new OkHttpClient();
    private Schedule getSchedule=new Schedule();
    public void run_get() throws Exception {
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder =HttpUrl.parse(" http://202.120.40.8:30335/jaccount/lessons")
                .newBuilder();
        urlBuilder.addQueryParameter("access_token", "12334");
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
        Gson gson=new Gson();
        getSchedule=gson.fromJson(response.body().string(),Schedule.class);

    }
    public Schedule getGetSchedule() {
        return getSchedule;
    }

//    public void setGetSchedule(Schedule getSchedule) {
//        this.getSchedule = getSchedule;
//    }
}
