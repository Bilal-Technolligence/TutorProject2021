package com.example.project2020_2021.Databases;

public class StuUserHelperClass {

    String stuname, stutype, stuemail, stupass, stucountry, stucity, stuaddress, stuphone, stuteachertype, stussub, stugender, studate;

    public StuUserHelperClass(){}

    public StuUserHelperClass( String stuname, String stutype, String stuemail, String stupass, String stucountry, String stucity,
                               String stuaddress, String stuphone, String stuteachertype, String stussub, String stugender, String studate) {
        this.stuname = stuname;
        this.stutype = stutype;
        this.stuemail = stuemail;
        this.stupass = stupass;
        this.stucountry = stucountry;
        this.stucity = stucity;
        this.stuaddress = stuaddress;
        this.stuphone = stuphone;
        this.stuteachertype = stuteachertype;
        this.stussub = stussub;
        this.stugender = stugender;
        this.studate = studate;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getStutype() {
        return stutype;
    }

    public void setStutype(String stutype) {
        this.stutype = stutype;
    }

    public String getStuemail() {
        return stuemail;
    }

    public void setStuemail(String stuemail) {
        this.stuemail = stuemail;
    }

    public String getStupass() {
        return stupass;
    }

    public void setStupass(String stupass) {
        this.stupass = stupass;
    }

    public String getStucountry() {
        return stucountry;
    }

    public void setStucountry(String stucountry) {
        this.stucountry = stucountry;
    }

    public String getStucity() {
        return stucity;
    }

    public void setStucity(String stucity) {
        this.stucity = stucity;
    }

    public String getStuaddress() {
        return stuaddress;
    }

    public void setStuaddress(String stuaddress) {
        this.stuaddress = stuaddress;
    }

    public String getStuphone() {
        return stuphone;
    }

    public void setStuphone(String stuphone) {
        this.stuphone = stuphone;
    }

    public String getStuteachertype() {
        return stuteachertype;
    }

    public void setStuteachertype(String stuteachertype) {
        this.stuteachertype = stuteachertype;
    }

    public String getStussub() {
        return stussub;
    }

    public void setStussub(String stussub) {
        this.stussub = stussub;
    }

    public String getStugender() {
        return stugender;
    }

    public void setStugender(String stugender) {
        this.stugender = stugender;
    }

    public String getStudate() {
        return studate;
    }

    public void setStudate(String studate) {
        this.studate = studate;
    }
}
