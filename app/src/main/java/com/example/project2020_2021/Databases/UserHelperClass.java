package com.example.project2020_2021.Databases;

public class UserHelperClass {

    String insname, instype, insemail, inspass, inscountry, city, address, phoneno;

   public UserHelperClass(){}

    public UserHelperClass(String insname, String instype, String insemail, String inspass, String inscountry, String city, String address, String phoneno) {
        this.insname = insname;
        this.instype = instype;
        this.insemail = insemail;
        this.inspass = inspass;
        this.inscountry = inscountry;
        this.city = city;
        this.address = address;
        this.phoneno = phoneno;
    }

    public String getInsname() {
        return insname;
    }

    public void setInsname(String insname) {
        this.insname = insname;
    }

    public String getInstype() {
        return instype;
    }

    public void setInstype(String instype) {
        this.instype = instype;
    }

    public String getInsemail() {
        return insemail;
    }

    public void setInsemail(String insemail) {
        this.insemail = insemail;
    }

    public String getInspass() {
        return inspass;
    }

    public void setInspass(String inspass) {
        this.inspass = inspass;
    }

    public String getInscountry() {
        return inscountry;
    }

    public void setInscountry(String inscountry) {
        this.inscountry = inscountry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

}
