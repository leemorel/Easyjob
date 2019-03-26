package com.easyjob.android;

import cn.bmob.v3.BmobObject;

public class Complaint_Info extends BmobObject{
    private String complaint;
    private Partimer_Info partimer;
    private Recruiter_Info recruiter;

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
