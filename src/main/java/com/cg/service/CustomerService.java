package com.cg.service;

import com.cg.dto.LoginRequest;
import com.cg.dto.LoginResponse;
import com.cg.dto.RegisterRequest;
import com.cg.entity.Customer;

public interface CustomerService {
    Customer registerCustomer(RegisterRequest request);
    LoginResponse loginCustomer(LoginRequest request);
}