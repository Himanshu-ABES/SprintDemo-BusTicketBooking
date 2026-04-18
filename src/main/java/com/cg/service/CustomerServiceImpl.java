package com.cg.service;

import com.cg.dto.LoginRequest;
import com.cg.dto.LoginResponse;
import com.cg.dto.RegisterRequest;
import com.cg.entity.Customer;
import com.cg.entity.Role;
import com.cg.entity.RolePk;
import com.cg.entity.User;
import com.cg.exception.DuplicateCustomerException;
import com.cg.exception.InvalidCredentialsException;
import com.cg.repository.CustomerRepository;
import com.cg.repository.UserRepository;
import com.cg.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    @Transactional
    public Customer registerCustomer(RegisterRequest request) {
        // Check for duplicate username
        if (customerRepo.existsByUsername(request.getUsername())) {
            throw new DuplicateCustomerException("Username '" + request.getUsername() + "' is already taken.");
        }
        // Check for duplicate email
        if (customerRepo.existsByEmail(request.getEmail())) {
            throw new DuplicateCustomerException("Email '" + request.getEmail() + "' is already registered.");
        }
        // Check for duplicate phone number
        if (customerRepo.existsByPhoneNo(request.getPhoneNo())) {
            throw new DuplicateCustomerException("Phone number '" + request.getPhoneNo() + "' is already registered.");
        }

        // 1. Create User (auth entity) with encoded password
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);

        // 2. Assign ROLE_USER
        Role role = new Role(new RolePk(request.getUsername(), "ROLE_USER"), user);
        user.setAuthorities(List.of(role));
        userRepo.save(user);

        // 3. Create Customer (business entity)
        Customer customer = new Customer();
        customer.setUsername(request.getUsername());
        customer.setCustName(request.getCustName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNo(request.getPhoneNo());
        return customerRepo.save(customer);
    }

    @Override
    public LoginResponse loginCustomer(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password.");
        }

        Customer customer = customerRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password."));

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password."));

        List<String> roles = user.getAuthorities().stream()
                .map(Role::getAuthority)
                .toList();

        String token = jwtService.generateToken(request.getUsername(), roles);

        return new LoginResponse(customer.getCustId(), customer.getCustName(), token, "Login successful");
    }
}