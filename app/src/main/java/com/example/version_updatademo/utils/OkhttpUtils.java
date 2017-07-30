package com.example.version_updatademo.utils;


import com.squareup.okhttp.OkHttpClient;

/**
 * Created by on 2017/7/21.
 */

public class OkhttpUtils {
    private static OkhttpUtils okhttpUtils=new OkhttpUtils();
    private static OkHttpClient client;

    private OkhttpUtils(){
        client=new OkHttpClient();
    }
    public static OkHttpClient getInstance(){
        return client;
    }

}
