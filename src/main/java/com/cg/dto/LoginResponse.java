package com.cg.dto;

public class LoginResponse {
    private Long custId;
    private String custName;
    private String message;

    public LoginResponse(Long custId, String custName, String message) {
        this.custId = custId;
        this.custName = custName;
        this.message = message;
    }

    public Long getCustId() { return custId; }
    public String getCustName() { return custName; }
    public String getMessage() { return message; }
}