package com.zhanghao.wifiqrsign.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ljx on 2016/5/4.
 */
public class SharedPreHelper {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    private String Name;
    private String Loc;

    public SharedPreHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("LoginIn", Context.MODE_WORLD_READABLE);
        editor = sharedPreferences.edit();
    }

    public String getLoc() {
        Loc = sharedPreferences.getString("location", null);
        return Loc;
    }

    public void setLoc(String loc) {
        Loc = loc;
    }

    public String getName() {
        Name = sharedPreferences.getString("name", null);
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
