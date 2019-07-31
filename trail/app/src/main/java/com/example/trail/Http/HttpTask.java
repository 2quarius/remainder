package com.example.trail.Http;

import android.os.Environment;
import java.io.File;
import java.io.IOException;

import okhttp3.*;

public class HttpTask {
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private final OkHttpClient client_post = new OkHttpClient();

    public void run_post() throws Exception {
        File file = new File("tasks.json");

        Request request = new Request.Builder()
                .url("https://...")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        Response response = client_post.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }
    private final OkHttpClient client_get = new OkHttpClient();

    public void run_get() throws Exception {
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder =HttpUrl.parse("https://.../tasks.json")
                .newBuilder();
        urlBuilder.addQueryParameter("user", "1234");
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();

//        Request request = new Request.Builder()
//                .url("http://.../tasks.json")
//                .build();

        Response response = client_get.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        System.out.println(response.body().string());
    }
//    OkHttpClientManager.getAsyn("https://www.baidu.com", new OkHttpClientManager.ResultCallback<String>()
//    {
//        @Override
//        public void onError(Request request, Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        @Override
//        public void onResponse(String u)
//        {
//            mTv.setText(u);//注意这里是UI线程
//        }
//    });
//    OkHttpClientManager.downloadAsyn(
//            "http://192.168.1.103:8080/okHttpServer/files/messenger_01.png",
//            Environment.getExternalStorageDirectory().getAbsolutePath(),
//new OkHttpClientManager.ResultCallback<String>()
//    {
//        @Override
//        public void onError(Request request, IOException e)
//        {
//
//        }
//
//        @Override
//        public void onResponse(String response)
//        {
//            //文件下载成功，这里回调的reponse为文件的absolutePath
//        }
//    });
//
//
//    File file = new File(Environment.getExternalStorageDirectory(), "tasks.json");
//
//    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//
//    RequestBody requestBody = new MultipartBuilder()
//            .type(MultipartBuilder.FORM)
//            .addPart(Headers.of(
//                    "Content-Disposition",
//                    "form-data; name=\"username\""),
//                    RequestBody.create(null, "张鸿洋"))
//            .addPart(Headers.of(
//                    "Content-Disposition",
//                    "form-data; name=\"mFile\";
//                    filename=\"wjd.mp4\""), fileBody)
//            .build();
//
//    Request request = new Request.Builder()
//            .url("http://192.168.1.103:8080/okHttpServer/fileUpload")
//            .post(requestBody)
//            .build();
//
//    Call call = mOkHttpClient.newCall(request);
//call.enqueue(new Callback()
//    {
//        //...
//    });


}


