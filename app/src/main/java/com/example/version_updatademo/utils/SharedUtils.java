package com.example.version_updatademo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by on 2017/7/25.
 */

public class SharedUtils {
    private static SharedPreferences sp;
    private static SharedUtils sharedUtils;

    private SharedUtils(Context context,String str){
        sp=context.getSharedPreferences(str,Context.MODE_PRIVATE);
    }
    public static SharedUtils getInstance(Context context,String str){
        if (sharedUtils!=null){
            sharedUtils=new SharedUtils(context,str);
        }
        return sharedUtils;
    }

    public static void saveSP(String key,Object value){
        SharedPreferences.Editor editor=sp.edit();

        if (value instanceof String){
            editor.putString(key, (String) value);
        }else if(value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }else if (value instanceof Float){
            editor.putFloat(key, (Float) value);
        }else if (value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }else if (value instanceof Long){
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    public static  Object getSP(String key,Object def){

        if (def instanceof Integer){
            return sp.getInt(key, (Integer) def);
        }else if (def instanceof String){
            return sp.getString(key, (String) def);
        }else if (def instanceof Float){
            return sp.getFloat(key, (Float) def);
        }else if (def instanceof Boolean){
            return sp.getBoolean(key, (Boolean) def);
        }else if (def instanceof Long){
            return sp.getLong(key, (Long) def);
        }
        return null;
    }
}
