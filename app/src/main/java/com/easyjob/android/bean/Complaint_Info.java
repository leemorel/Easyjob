package com.easyjob.android.bean;

import cn.bmob.v3.BmobObject;

public class Complaint_Info extends BmobObject{
    private String complaint;
    private Partimer_Info partimer;
    private Recruiter_Info recruiter;
    private String complainter;
    private String company;

    public String getComplainter() {
        return complainter;
    }

    public void setComplainter(String complainter) {
        this.complainter = complainter;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Partimer_Info getPartimer() {
        return partimer;
    }

    public void setPartimer(Partimer_Info partimer) {
        this.partimer = partimer;
    }

    public Recruiter_Info getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter_Info recruiter) {
        this.recruiter = recruiter;
    }
}
