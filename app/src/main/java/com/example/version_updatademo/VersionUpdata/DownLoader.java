package com.example.version_updatademo.VersionUpdata;

import android.app.Activity;
import android.os.Environment;

import com.example.version_updatademo.utils.OkhttpUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by on 2017/7/21.
 */

public class DownLoader {
    public static void downLoadAndInstallApk(Activity activity,String url){
        Request builder=new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call= OkhttpUtils.getInstance().newCall(builder);
        call.enqueue(new DownLoaderCallBack(activity));
    }
    private static class DownLoaderCallBack implements Callback {
        private Activity act;
        private DownLoaderCallBack(Activity act){
            this.act=act;
        }


        @Override
        public void onFailure(Request request, IOException e) {

        }

        @Override
        public void onResponse(Response response) throws IOException {
            if (response.isSuccessful()){
                byte[] bytes = response.body().bytes();
                if (bytes!=null&&bytes.length>0){
                    File dir = new File(Environment.getExternalStorageDirectory()+"/how");
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                    File file = new File(dir, "how.apk");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes);

                    fos.close();
                    bytes=null;
                    //然后就要安装了
                    InstallApk.InstallApk(act,file);
                }
            }
        }
    }
}
