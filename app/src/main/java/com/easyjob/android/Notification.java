package com.easyjob.android;

import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class Notification  extends BmobObject{
    private String notification;
    private String updateAt_data;
    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }


    public String getUpdateAt_data() {
        return updateAt_data;
    }

    public void setUpdateAt_data(String updateAt_data) {
        this.updateAt_data = updateAt_data;
    }
}
