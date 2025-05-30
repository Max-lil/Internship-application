package com.example.internshipapplication.Company;

public class Company {
    private int id;
    private String companyName;
    private String companyAddress;

    public Company(int id, String companyName, String companyAddress) {
        this.id = id;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
