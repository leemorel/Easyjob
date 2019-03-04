package com.easyjob.android;

import cn.bmob.v3.BmobObject;

public class Recruiter_Info extends BmobObject {
    private String recruiter_company;
    private String recruiter_introduction;
    private String recruiter_phone;

    public String getRecruiter_phone() {
        return recruiter_phone;
    }

    public void setRecruiter_phone(String recruiter_phone) {
        this.recruiter_phone = recruiter_phone;
    }

    private String recruiter_email;
    private String getRecruiter_address;

    public String getRecruiter_company() {
        return recruiter_company;
    }

    public void setRecruiter_company(String recruiter_company) {
        this.recruiter_company = recruiter_company;
    }

    public String getRecruiter_introduction() {
        return recruiter_introduction;
    }

    public void setRecruiter_introduction(String recruiter_introduction) {
        this.recruiter_introduction = recruiter_introduction;
    }


    public String getRecruiter_email() {
        return recruiter_email;
    }

    public void setRecruiter_email(String recruiter_email) {
        this.recruiter_email = recruiter_email;
    }

    public String getGetRecruiter_address() {
        return getRecruiter_address;
    }

    public void setGetRecruiter_address(String getRecruiter_address) {
        this.getRecruiter_address = getRecruiter_address;
    }
}
