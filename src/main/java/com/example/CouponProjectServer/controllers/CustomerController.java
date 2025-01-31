package com.example.CouponProjectServer.controllers;

import com.example.CouponProjectServer.beans.Coupon;
import com.example.CouponProjectServer.beans.Customer;
import com.example.CouponProjectServer.exceptions.CouponOutOfStockException;
import com.example.CouponProjectServer.exceptions.WrongDetailsException;
import com.example.CouponProjectServer.services.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Transactional
    @PostMapping("add/purchase/{couponID}")
    // this method checks if coupon is available to purchase for a specific customer if so than adds purchase to the database
    public void purchaseCoupon(@PathVariable int couponID) throws WrongDetailsException, CouponOutOfStockException {
        customerService.purchaseCoupon(couponID);
    }

    @GetMapping("owned/coupons")
    // this method gets all coupons owned by a specific customer
    public List<Coupon> getCustomerCoupons() throws NoSuchElementException {
        return customerService.getCustomerCoupons();
    }

    @GetMapping("category")
    // this method gets all coupons with specific category that belongs to one customer
    public List<Coupon> getCustomerCoupons(@PathVariable int categoryID) throws WrongDetailsException {
        return customerService.getCustomerCoupons(categoryID);
    }

    @GetMapping("maxprice")
    // this method gets all coupons whose price is lower than max price given belongs to one customer
    public List<Coupon> getCustomerCoupons(@PathVariable Double maxPrice) throws WrongDetailsException {
        return customerService.getCustomerCoupons(maxPrice);
    }

    @GetMapping("detail/customer")
    // this method gets a single customer data using id
    public Customer getCustomerDetails() throws NoSuchElementException, WrongDetailsException {
        return customerService.getCustomerDetails();
    }

    @GetMapping("detail/coupon")
    // this method gets a single coupon data using id
    public Coupon getOneCoupon(@PathVariable int couponID) throws WrongDetailsException {
        return customerService.getOneCoupon(couponID);
    }

    @GetMapping("coupons")
    public List<Coupon> getAllCoupons() {
        return customerService.getAllCoupons();
    }
}
