package com.easyjob.android;

import cn.bmob.v3.BmobObject;

public class ParttimeJob extends BmobObject{
    private String j_title;
    private String j_salary;
    private String j_address;
    private String j_requirement;
    private String j_details;
    private String j_time;
    private Recruiter_Info company;

    public String getJ_title() {
        return j_title;
    }

    public void setJ_title(String j_title) {
        this.j_title = j_title;
    }

    public String getJ_salary() {
        return j_salary;
    }

    public void setJ_salary(String j_salary) {
        this.j_salary = j_salary;
    }

    public String getJ_address() {
        return j_address;
    }

    public void setJ_address(String j_address) {
        this.j_address = j_address;
    }

    public String getJ_requirement() {
        return j_requirement;
    }

    public void setJ_requirement(String j_requirement) {
        this.j_requirement = j_requirement;
    }

    public String getJ_details() {
        return j_details;
    }

    public void setJ_details(String j_details) {
        this.j_details = j_details;
    }

    public String getJ_time() {
        return j_time;
    }

    public void setJ_time(String j_time) {
        this.j_time = j_time;
    }


    public Recruiter_Info getCompany() {
        return company;
    }

    public void setCompany(Recruiter_Info company) {
        this.company = company;
    }
}
