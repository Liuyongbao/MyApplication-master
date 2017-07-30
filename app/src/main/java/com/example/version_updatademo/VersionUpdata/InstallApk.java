package com.example.version_updatademo.VersionUpdata;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by on 2017/7/21.
 */

public class InstallApk {
    public static void InstallApk(Activity activity, File file){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://"+file.getAbsolutePath()),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }
}
