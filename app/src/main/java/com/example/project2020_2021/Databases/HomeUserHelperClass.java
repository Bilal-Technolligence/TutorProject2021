package com.example.project2020_2021.Databases;

public class HomeUserHelperClass {

    String husername, huseremail, huserpassword;

    public HomeUserHelperClass(){}

    public HomeUserHelperClass( String husername, String huseremail, String huserpassword) {

        this.husername = husername;
        this.huseremail = huseremail;
        this.huserpassword = huserpassword;

    }

    public String getHusername() {
        return husername;
    }

    public void setHusername(String husername) {
        this.husername = husername;
    }

    public String getHuseremail() {
        return huseremail;
    }

    public void setHuseremail(String huseremail) {
        this.huseremail = huseremail;
    }

    public String getHuserpassword() {
        return huserpassword;
    }

    public void setHuserpassword(String huserpassword) {
        this.huserpassword = huserpassword;
    }
}
