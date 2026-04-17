package com.cg.dto;

public class RegisterRequest {
    private String custName;
    private String phoneNo;
    private String password;

    public String getCustName() { return custName; }
    public void setCustName(String custName) { this.custName = custName; }
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}