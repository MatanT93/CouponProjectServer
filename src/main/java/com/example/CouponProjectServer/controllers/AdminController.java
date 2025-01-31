package com.example.CouponProjectServer.controllers;

import com.example.CouponProjectServer.beans.Company;
import com.example.CouponProjectServer.beans.Customer;
import com.example.CouponProjectServer.exceptions.NameAlreadyExistException;
import com.example.CouponProjectServer.exceptions.WrongDetailsException;
import com.example.CouponProjectServer.services.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // this method checks if company name and email exist before adding the company to the database
    @PostMapping("add/company")
    public Company addCompany(@RequestBody Company company) throws NameAlreadyExistException {
        return adminService.addCompany(company);
    }

    // this method updates company's data excluding company ID and name changes
    @PutMapping("update/company/{id}")
    public Company updateCompany(@PathVariable int id, @RequestBody Company company) throws NoSuchElementException, WrongDetailsException {
        return adminService.updateCompany(id, company);
    }

    // this method deletes company by first deleting record from 2 other tables
    @Transactional
    @DeleteMapping("delete/company/{id}")
    public void deleteCompany(@PathVariable int id) throws NoSuchElementException {
        adminService.deleteCompany(id);
    }

    // this method returns a list of all companies from the database
    @GetMapping("companies")
    public List<Company> getAllCompanies() throws NoSuchElementException {
        return adminService.getAllCompanies();
    }

    // this method return specific company from the database using ID
    @GetMapping("company/{id}")
    public Company getOneCompany(@PathVariable int id) throws WrongDetailsException {
        return adminService.getOneCompany(id);
    }

    // this method adds customer to the database excluding if email already exist
    @PostMapping("add/customer")
    public Customer addCustomer(@RequestBody Customer customer) throws NameAlreadyExistException {
        return adminService.addCustomer(customer);
    }

    @PutMapping("update/customer")
    // this method updates customer's data excluding customer ID
    public void updateCustomer(@RequestBody Customer customer) throws NoSuchElementException, WrongDetailsException {
        adminService.updateCustomer(customer);
    }

    @Transactional
    @DeleteMapping("delete/customer/{id}")
    // this method deletes customer by first deleting record from another table
    public void deleteCustomer(@PathVariable int id) {
        adminService.deleteCustomer(id);
    }

    @GetMapping("customers")
    // this method returns a list of all customer from the database
    public List<Customer> getAllCustomers() throws NoSuchElementException {
        return adminService.getAllCustomers();
    }

    @GetMapping("customer/{id}")
    // this method returns specific customer from the database using ID
    public Customer getOneCustomer(@PathVariable int id) throws WrongDetailsException {
        return adminService.getOneCustomer(id);
    }

}
