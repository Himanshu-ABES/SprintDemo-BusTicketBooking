package com.cg.dto;

public class LoginResponse {
    private Long custId;
    private String custName;
    private String token;
    private String message;

    public LoginResponse(Long custId, String custName, String token, String message) {
        this.custId = custId;
        this.custName = custName;
        this.token = token;
        this.message = message;
    }

    public Long getCustId() { return custId; }
    public String getCustName() { return custName; }
    public String getToken() { return token; }
    public String getMessage() { return message; }
}