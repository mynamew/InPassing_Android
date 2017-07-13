package com.quhuanbei.quhuanbei.ali;



import android.provider.SyncStateContract;

import java.text.DecimalFormat;

/**
 * Created by Master on 2016/10/29.
 */
public class PayItem{

    private String partner;
    private String rsakey;
    private String seller;

    private String notiURL;
    private String money;
    private String orderID;
    private String title;
    private String desc;

    public PayItem(){
        //
    }

    //废弃，之前类型为double时转换用
//    public String getMoney() {
//        DecimalFormat df = new DecimalFormat("0.00");
//        String orderMoney = df.format(money);
//        return orderMoney;
//    }

    public String getMoney() {
        return money;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public PayItem setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public PayItem setMoney(String money) {
        this.money = money;
        return this;
    }

    public PayItem setOrderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public PayItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getNotiURL() {
        return notiURL;
    }

    public PayItem setNotiURL(String notiURL) {
        this.notiURL = notiURL;
        return this;
    }

    public String getSeller() {
        return seller;
    }

    public PayItem setSeller(String seller) {
        this.seller = seller;
        return this;
    }

    public String getPartner() {
        return partner;
    }

    public PayItem setPartner(String partner) {
        this.partner = partner;
        return this;
    }

    public String getRsakey() {
        return rsakey;
    }

    public PayItem setRsakey(String rsakey) {
        this.rsakey = rsakey;
        return this;
    }



}
