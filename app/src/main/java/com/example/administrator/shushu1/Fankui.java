package com.example.administrator.shushu1;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/6/13.
 */

public class Fankui extends BmobObject
{
    private String name;
    private String xinxi;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXinxi() {
        return xinxi;
    }

    public void setXinxi(String xinxi) {
        this.xinxi = xinxi;
    }
}
