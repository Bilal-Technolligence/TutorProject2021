package com.example.project2020_2021.Databases;

public class VacancyHelperClass {

    private String JobTitle, JobReq, JobDutyRes;
    private Integer Salary;

    public VacancyHelperClass()
    {

    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getJobReq() {
        return JobReq;
    }

    public void setJobReq(String jobReq) {
        JobReq = jobReq;
    }

    public String getJobDutyRes() {
        return JobDutyRes;
    }

    public void setJobDutyRes(String jobDutyRes) {
        JobDutyRes = jobDutyRes;
    }

    public Integer getSalary() {
        return Salary;
    }

    public void setSalary(Integer salary) {
        Salary = salary;
    }
}
