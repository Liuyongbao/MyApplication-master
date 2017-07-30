package com.example.version_updatademo.VersionUpdata;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.example.version_updatademo.R;
import com.example.version_updatademo.utils.DialogUtils;
import com.example.version_updatademo.utils.PackageUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;



public class MainActivity extends AppCompatActivity implements Callback {

    private OkHttpClient client;
    private Request.Builder builder;
    private String url="https://guaju.github.io/versioninfo.json";
    private Call call;
    private GsonInfo.DataBean data;
    private String currentVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkUpdata(url,this);
    }

    private void checkUpdata(String url, Callback callback) {
        client = new OkHttpClient();
        builder = new Request.Builder();
        Request request=builder.get()
                .url(url)
                .build();
        call = client.newCall(request);
        call.enqueue(callback);
    }

    private void parseJson(String json){
        if (TextUtils.isEmpty(json)){
            return;
        }
        Gson gson=new Gson();
        GsonInfo gsonInfo = gson.fromJson(json, GsonInfo.class);

        if ("200".equals(gsonInfo.getStatus())){
            data = gsonInfo.getData();
            String verstion=data.getVersion();
            try {
                currentVersion = PackageUtils.getCurrentVersion(this);

                if (!TextUtils.isEmpty(verstion)){
                    if (!currentVersion.equals(verstion)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("aaaaaaaa", "run: "+ data.getAppurl());
                                DialogUtils.showUpdateDialog(MainActivity.this,"版本更新",data.getInfo(),data.getAppurl());
                            }
                        });
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (response.isSuccessful()){
            String json = response.body().string();
            parseJson(json);
        }
    }
}
