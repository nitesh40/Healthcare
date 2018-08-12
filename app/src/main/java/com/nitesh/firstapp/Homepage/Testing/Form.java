package com.nitesh.firstapp.Homepage.Testing;

/**
 * Created by Nitesh on 8/11/2018.
 */

public class Form {

    String mname,mid,mdescription;
    public Form() {

    }

    public Form(String mname, String mid, String mdescription) {
        this.mname = mname;
        this.mid = mid;
        this.mdescription=mdescription;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String id) {
        this.mid = mid;
    }

    public String getMdescription() {
        return mdescription;
    }

    public void setDescription(String mdescription) {this.mdescription = mdescription;}

}
