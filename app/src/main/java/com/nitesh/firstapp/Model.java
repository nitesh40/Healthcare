package com.nitesh.firstapp;

/**
 * Created by Nitesh on 6/2/2018.
 */

public class Model {

    String fname,username,password,cpassword,email,phone,address,male,femlae,checkbox,checkbox1,id;

    public Model() {


    }

    public Model(String fname, String username, String password, String cpassword, String email, String phone, String address, String male, String femlae, String checkbox, String checkbox1, String id) {
        this.fname=fname;
        this.username = username;
        this.password = password;
        this.cpassword=cpassword;
        this.email = email;
        this.phone = phone;
        this.address=address;
        this.male=male;
        this.femlae=femlae;
        this.checkbox=checkbox;
        this.checkbox1=checkbox1;
        this.id=id;
    }


    public String getFname() { return fname; }

    public void setFname(String fname) { this.fname = fname; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address= address;
    }

    public String getMale() { return male; }

    public void setMale(String male) { this.male = male; }

    public String getFemlae() { return femlae; }

    public void setFemlae(String femlae) { this.femlae = femlae; }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox= checkbox;
    }

    public String getCheckbox1() {
        return checkbox1;
    }

    public void setCheckbox1(String checkbox1) {
        this.checkbox1= checkbox1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id= id;
    }
}
