package com.cg.service;

import com.cg.dto.LoginRequest;
import com.cg.dto.LoginResponse;
import com.cg.dto.RegisterRequest;
import com.cg.entity.Customer;
import com.cg.exception.DuplicateCustomerException;
import com.cg.exception.InvalidCredentialsException;
import com.cg.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public Customer registerCustomer(RegisterRequest request) {
        if (customerRepo.findByPhoneNo(request.getPhoneNo()).isPresent()) {
            throw new DuplicateCustomerException("Phone number already registered.");
        }
        Customer customer = new Customer();
        customer.setCustName(request.getCustName());
        customer.setPhoneNo(request.getPhoneNo());
        customer.setPassword(request.getPassword());
        return customerRepo.save(customer);
    }

    @Override
    public LoginResponse loginCustomer(LoginRequest request) {
        Customer customer = customerRepo.findByPhoneNo(request.getPhoneNo())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid phone number or password."));
        
        if (!customer.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException("Invalid phone number or password.");
        }
        
        return new LoginResponse(customer.getCustId(), customer.getCustName(), "Login successful");
    }
}