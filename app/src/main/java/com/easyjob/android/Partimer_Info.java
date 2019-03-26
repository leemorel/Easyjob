package com.easyjob.android;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Partimer_Info extends BmobObject {
    private String p_name;
    private String p_age;
    private String p_phone;
    private String p_email;
    private String p_education;
    private BmobFile p_avatar;
    private String p_sex;
    private String p_height;
    private String p_introduction;

    public String getP_introduction() {
        return p_introduction;
    }

    public void setP_introduction(String p_introduction) {
        this.p_introduction = p_introduction;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_age() {
        return p_age;
    }

    public void setP_age(String p_age) {
        this.p_age = p_age;
    }

    public String getP_phone() {
        return p_phone;
    }

    public void setP_phone(String p_phone) {
        this.p_phone = p_phone;
    }

    public String getP_email() {
        return p_email;
    }

    public void setP_email(String p_email) {
        this.p_email = p_email;
    }

    public String getP_education() {
        return p_education;
    }

    public void setP_education(String p_education) {
        this.p_education = p_education;
    }

    public BmobFile getP_avatar() {
        return p_avatar;
    }

    public void setP_avatar(BmobFile p_avatar) {
        this.p_avatar = p_avatar;
    }

    public String getP_sex() {
        return p_sex;
    }

    public void setP_sex(String p_sex) {
        this.p_sex = p_sex;
    }



    public String getP_height() {
        return p_height;
    }

    public void setP_height(String p_height) {
        this.p_height = p_height;
    }
}
