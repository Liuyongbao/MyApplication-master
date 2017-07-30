package com.example.version_updatademo.Guide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.version_updatademo.Guide.progress.ProgressImageView;
import com.example.version_updatademo.Guide.progress.ProgressModelLoader;
import com.example.version_updatademo.R;
import com.example.version_updatademo.Splash.SplashActivity;
import com.example.version_updatademo.utils.OkhttpUtils;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends AppCompatActivity {

    private ViewPager vp;
    private LinearLayout ll;
    private List<String> guidepic;
    private List<ProgressImageView> list=new ArrayList<>();
    private ImageView[] imgs;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    private void initData() {
        OkHttpClient client = OkhttpUtils.getInstance();
        final Request request=new Request.Builder()
                .get()
                .url(HttpConstants.guide)
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    String json = response.body().string();
                    Gson gson=new Gson();
                    GuideInfo guideInfo = gson.fromJson(json, GuideInfo.class);
                    if (guideInfo!=null){
                        if (200==guideInfo.getStatus()){
                            guidepic = guideInfo.getData().getGuidepic();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updataFace();
                                }
                            });
                        }
                    }
                }
            }

        });
    }

    private void updataFace() {
        for (String str:guidepic){
            ProgressImageView pgImg= (ProgressImageView) LayoutInflater.from(this)
                    .inflate(R.layout.progressimageview,null,false);
            ImageView imageView=pgImg.getImageView();
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this)
                    .using(new ProgressModelLoader(new ProgressHandler(GuideActivity.this,pgImg)))
                    .load(str)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.loading).into(imageView);
            list.add(pgImg);

        }
        GuideAdapter adapter=new GuideAdapter(this,list);
        vp.setAdapter(adapter);
        initbottom();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        ll = (LinearLayout) findViewById(R.id.ll);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                btn.setBackground(getResources().getDrawable(R.drawable.button_to));
                startActivity(new Intent(GuideActivity.this, SplashActivity.class));

            }
        });
    }
    private static class ProgressHandler extends Handler{
        private final WeakReference<Activity> mActivity;
        private final ProgressImageView mProgressImageView;

        public ProgressHandler(Activity activity, ProgressImageView progressImageView){
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(activity);
            mProgressImageView = progressImageView;
        }



        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity=mActivity.get();
            if (activity!=null){
                switch (msg.what){
                    case 1:
                        int percent = msg.arg1 * 100 / msg.arg2;
                        mProgressImageView.setProgress(percent);
                        if (percent>=100){
                            mProgressImageView.hideTextView();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void initbottom(){
        imgs = new ImageView[list.size()];
        for(int j=0;j<imgs.length;j++){
            ImageView img=new ImageView(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(50,50);
            params.setMargins(10,0,10,0);
            img.setLayoutParams(params);
            if (j==0){
                img.setImageResource(R.drawable.yd);
            }else {
                img.setImageResource(R.drawable.normal);
            }
            imgs[j]=img;
            ll.addView(img);
        }
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("NewApi")
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imgs.length; i++) {
                    int p = position % list.size();
                    if (i == p) {
                        imgs[i].setImageResource(R.drawable.yd);
                    } else {
                        imgs[i].setImageResource(R.drawable.normal);
                    }
                    if (position==list.size()-1){
                        btn.setVisibility(View.VISIBLE);
                        Animation animation= AnimationUtils.loadAnimation(GuideActivity.this,R.anim.from_button);
                        animation.setInterpolator(new OvershootInterpolator());
                        btn.setAnimation(animation);
                    }else {
                        btn.setVisibility(View.GONE);
                        btn.setBackground(getResources().getDrawable(R.drawable.button));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
