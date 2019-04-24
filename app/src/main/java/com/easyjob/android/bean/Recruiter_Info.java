package com.easyjob.android.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Recruiter_Info extends BmobObject {
    private String recruiter_company;
    private String recruiter_profile;
    private String recruiter_phone;
    private String recruiter_email;
    private String recruiter_address;
    private BmobFile recruiter_avatar;

    public BmobFile getRecruiter_avatar() {
        return recruiter_avatar;
    }

    public void setRecruiter_avatar(BmobFile recruiter_avatar) {
        this.recruiter_avatar = recruiter_avatar;
    }

    public String getRecruiter_address() {
        return recruiter_address;
    }
    public String getRecruiter_profile() {
        return recruiter_profile;
    }

    public void setRecruiter_profile(String recruiter_profile) {
        this.recruiter_profile = recruiter_profile;
    }
    public void setRecruiter_address(String recruiter_address) {
        this.recruiter_address = recruiter_address;
    }


    public String getRecruiter_phone() {
        return recruiter_phone;
    }

    public void setRecruiter_phone(String recruiter_phone) {
        this.recruiter_phone = recruiter_phone;
    }


    public String getRecruiter_company() {
        return recruiter_company;
    }

    public void setRecruiter_company(String recruiter_company) {
        this.recruiter_company = recruiter_company;
    }

    public String getRecruiter_introduction() {
        return recruiter_profile;
    }

    public void setRecruiter_introduction(String recruiter_introduction) {
        this.recruiter_profile = recruiter_introduction;
    }


    public String getRecruiter_email() {
        return recruiter_email;
    }

    public void setRecruiter_email(String recruiter_email) {
        this.recruiter_email = recruiter_email;
    }

}
