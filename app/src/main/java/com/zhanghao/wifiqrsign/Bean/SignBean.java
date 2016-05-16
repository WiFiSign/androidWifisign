package com.zhanghao.wifiqrsign.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Ljx on 2016/5/4.
 */
public class SignBean extends BmobObject {
    private String name;
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
