package com.example.CouponProjectServer.controllers;

import com.example.CouponProjectServer.beans.Category;
import com.example.CouponProjectServer.beans.Company;
import com.example.CouponProjectServer.beans.Coupon;
import com.example.CouponProjectServer.exceptions.NameAlreadyExistException;
import com.example.CouponProjectServer.exceptions.WrongDetailsException;
import com.example.CouponProjectServer.services.CompanyService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("company")
public class CompanyController {

    @Autowired
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("add/coupon")
    // this method checks for coupons existence if not than adds coupon to the database
    public Coupon addCoupon(@RequestBody Coupon coupon) throws NameAlreadyExistException {
        return companyService.addCoupon(coupon);
    }

    @PutMapping("update/coupon")
    // this method updates coupon data excluding couponID and companyID
    public Coupon updateCoupon(@RequestBody Coupon coupon) throws WrongDetailsException {
        return companyService.updateCoupon(coupon);
    }

    @Transactional
    @DeleteMapping("delete/coupon/{couponID}")
    // this method deletes coupon using id
    public void deleteCoupon(@PathVariable int couponID) throws WrongDetailsException {
        companyService.deleteCoupon(couponID);
    }

    @GetMapping("coupons")
    // this method gets all coupons belongs to one company
    public List<Coupon> getCompanyCoupons() throws NoSuchElementException {
        return companyService.getCompanyCoupons();
    }

    @GetMapping("coupons/category")
    // this method gets all coupons with specific category that belongs to one company
    public List<Coupon> getCompanyCoupons(@RequestParam int categoryID) {
        return companyService.getCompanyCoupons(categoryID);
    }

    @GetMapping("coupons/maxprice")
    // this method gets all coupons whose price is lower than max price given belongs to one company
    public List<Coupon> getCompanyCoupons(@RequestParam Double maxPrice) {
        return companyService.getCompanyCoupons(maxPrice);
    }

    @GetMapping("detail")
    // this method gets a single company data using id
    public Company getCompanyDetails() throws WrongDetailsException {
        return companyService.getCompanyDetails();
    }

    @GetMapping("coupon/{id}")
    // this method gets a single coupon data using id
    public Coupon getOneCoupon(@PathVariable int id) throws WrongDetailsException {
        return companyService.getOneCoupon(id);
    }

    @GetMapping("/categories")
    // this method gets all categories
    public List<Category> getAllCategories() {
        return companyService.getAllCategories();
    }
}
