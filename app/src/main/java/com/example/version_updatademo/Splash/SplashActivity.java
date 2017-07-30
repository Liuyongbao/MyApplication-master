package com.example.version_updatademo.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.model.LottieComposition;
import com.bumptech.glide.Glide;
import com.example.version_updatademo.Guide.HttpConstants;
import com.example.version_updatademo.R;
import com.example.version_updatademo.VersionUpdata.MainActivity;
import com.example.version_updatademo.utils.OkhttpUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private int count = 0;
    private LottieAnimationView web;
    private Button btn;
    private ImageView img;
    private TextView tv;
    private SplashIfo splashIfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        useTimer(5000);
        initView();
        initData();
        //initWebView();
    }

    private void initWebView() {
        OkHttpClient instance = OkhttpUtils.getInstance();
        final Request request = new Request.Builder()
                .get()
                .url(HttpConstants.splash)
                .build();
        Call call = instance.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){

                    try {
                        JSONObject json=new JSONObject(response.body().string());
                        LottieComposition.fromJson(getResources(),json, new LottieComposition.OnCompositionLoadedListener() {
                            @Override
                            public void onCompositionLoaded(LottieComposition composition) {
                                setComposition(composition);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void initData() {
        OkHttpClient instance = OkhttpUtils.getInstance();
        final Request request = new Request.Builder()
                .get()
                .url(HttpConstants.splash)
                .build();
        Call call = instance.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    splashIfo = gson.fromJson(json, SplashIfo.class);
                    if (splashIfo != null) {
                        if (200 == splashIfo.getStatus()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    JsonData();
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private void JsonData() {
        SplashIfo.DataBean data = splashIfo.getData();
        tv.setText(data.getText_title());
        Glide.with(this)
                .load(data.getImg_icon())
                .into(img);
    }

    private void initView() {
        web = (LottieAnimationView) findViewById(R.id.animation_view);
        btn = (Button) findViewById(R.id.btn);
        img = (ImageView) findViewById(R.id.img);
        tv = (TextView) findViewById(R.id.tv);
    }

    private void useTimer(final long time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (count <= time) {
                            count += 1000;
                        } else {
                            timer.cancel();
                            timer.purge();
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                }, 0, 1000);
            }
        }).start();
    }

    private void setComposition(LottieComposition animationView){
        web.setProgress(0);
        web.loop(true);
        web.setComposition(animationView);
        web.playAnimation();
    }
}
