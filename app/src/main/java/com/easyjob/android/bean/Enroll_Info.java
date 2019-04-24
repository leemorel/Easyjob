package com.easyjob.android.bean;

import cn.bmob.v3.BmobObject;

public class Enroll_Info extends BmobObject {
    private Recruiter_Info recruiter;
    private Partimer_Info partimer;
    private ParttimeJob parttimejob;
    private Integer state;
    private String company;
    private String appyer;
    private String job;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAppyer() {
        return appyer;
    }

    public void setAppyer(String appyer) {
        this.appyer = appyer;
    }



    public Recruiter_Info getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter_Info recruiter) {
        this.recruiter = recruiter;
    }

    public Partimer_Info getPartimer() {
        return partimer;
    }

    public void setPartimer(Partimer_Info partimer) {
        this.partimer = partimer;
    }

    public ParttimeJob getParttimejob() {
        return parttimejob;
    }

    public void setParttimejob(ParttimeJob parttimejob) {
        this.parttimejob = parttimejob;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
