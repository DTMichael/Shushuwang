package com.example.administrator.shushu1;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/6/6.
 */

public class Allbook extends BmobObject
{
    private String name;
    private int money;
    private BmobFile image;
    private String url;
    private String from;
    private String lianxi;
    private int newrank;
    private String weixin;
    private String qq;
    public Allbook(String name,int money,BmobFile image,String lianxi,String from,int newrank) {
        this.setTableName("allbook");
        this.name=name;
        this.image=image;
        this.money=money;
        this.lianxi=lianxi;
        this.from=from;
        this.newrank=newrank;
    }
    public Allbook(){
        this.setTableName("allbook");
    }

    public int getNewrank(){
        return newrank;
    }
    public void setNewrank(int rank){
        this.newrank=rank;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLianxi() {
        return lianxi;
    }

    public void setLianxi(String lianxi) {
        this.lianxi = lianxi;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
