package com.example.CouponProjectServer.controllers;

import com.example.CouponProjectServer.beans.ClientType;
import com.example.CouponProjectServer.beans.Company;
import com.example.CouponProjectServer.beans.Customer;
import com.example.CouponProjectServer.beans.User;
import com.example.CouponProjectServer.exceptions.WrongDetailsException;
import com.example.CouponProjectServer.security.TokensManager;
import com.example.CouponProjectServer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@CrossOrigin
public class AuthController {

    @Autowired
    protected final CompanyService companyService;
    @Autowired
    protected final AdminService adminService;
    @Autowired
    protected final CustomerService customerService;
    @Autowired
    private final TokensManager tokensManager;
    public static int companyID;
    public static int customerID;

    public AuthController(CompanyService companyService, AdminService adminService, CustomerService customerService, TokensManager tokensManager) {
        this.companyService = companyService;
        this.adminService = adminService;
        this.customerService = customerService;
        this.tokensManager = tokensManager;
    }

    @ResponseBody
    @PostMapping(path = "login", consumes = "application/json")
    public ResponseEntity<String> login(@RequestBody User user) {
        ClientType type ;
        try {
            switch (user.getClientType()) {
                case "Admin" -> {
                    type = ClientType.Admin;
                    if (adminService.adminLogin(user.getEmail(), user.getPassword())) {
                        return ResponseEntity.ok(tokensManager.createToken(type));
                    }
                }
                case "Company" -> {
                    type = ClientType.Company;
                    if (companyService.existsByEmailAndPassword(user.getEmail(), user.getPassword())) {
                        Company company = companyService.findByEmailAndPassword(user.getEmail(), user.getPassword()).orElseThrow(() -> new WrongDetailsException("User not found"));
                        companyID = company.getId();
                        return ResponseEntity.ok(tokensManager.createToken(company, type));
                    }
                }
                case "Customer" -> {
                    type = ClientType.Customer;
                    if (customerService.existsByEmailAndPassword(user.getEmail(), user.getPassword())) {
                        Customer customer = customerService.findByEmailAndPassword(user.getEmail(), user.getPassword()).orElseThrow(() -> new WrongDetailsException("User not found"));
                        customerID = customer.getId();
                        return ResponseEntity.ok(tokensManager.createToken(customer, type));
                    }
                }
            }
        } catch (Exception | WrongDetailsException ignored) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    }

    @GetMapping("logout")
    public void logout(String token){TokensManager.activeTokens.remove(token);}

}
