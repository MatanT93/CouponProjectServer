package com.example.CouponProjectServer.services;

import com.example.CouponProjectServer.beans.Coupon;
import com.example.CouponProjectServer.beans.Customer;
import com.example.CouponProjectServer.beans.CustomerVSCoupon;
import com.example.CouponProjectServer.controllers.AuthController;
import com.example.CouponProjectServer.exceptions.CouponOutOfStockException;
import com.example.CouponProjectServer.exceptions.WrongDetailsException;
import com.example.CouponProjectServer.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService extends ClientService {

    public CustomerService(CategoryRepository categoryRepository, CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, CustomerVSCouponRepository customerVSCouponRepository) {
        super(categoryRepository, companyRepository, couponRepository, customerRepository, customerVSCouponRepository);
    }

    public boolean existsByEmailAndPassword(String email, String password) {
        return customerRepository.existsByEmailAndPassword(email, password);
    }

    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }

    @Transactional
    public void purchaseCoupon(int couponID) throws NoSuchElementException, CouponOutOfStockException, WrongDetailsException {
        try {
            Coupon coupon = couponRepository.findById(couponID).orElseThrow();
            if (customerVSCouponRepository.existsByCustomerIdAndCouponId(AuthController.customerID, couponID) || coupon.getAmount() == 0 || LocalDate.now().isAfter(coupon.getEndDate().toLocalDate()))
                throw new CouponOutOfStockException("unable to complete the transaction, check coupon detail and try again");
            else {
                CustomerVSCoupon newPurchase = new CustomerVSCoupon(customerRepository.findById(AuthController.customerID).orElseThrow(), couponRepository.findById(couponID).orElseThrow());
                customerVSCouponRepository.save(newPurchase);
                coupon.setAmount(coupon.getAmount() - 1);
                couponRepository.save(coupon);
            }
        } catch (NoSuchElementException e) {
            throw new WrongDetailsException();
        }
    }

    public List<Coupon> getCustomerCoupons() throws NoSuchElementException {
        try {
            List<CustomerVSCoupon> ownedCoupons = new ArrayList<>(customerVSCouponRepository.findByCustomerId(AuthController.customerID));
            List<Coupon> coupons = new ArrayList<>();
            for (CustomerVSCoupon c : ownedCoupons)
                coupons.add(couponRepository.findById(c.getCoupon()).orElseThrow(WrongDetailsException::new));
            return coupons;
        } catch (NoSuchElementException | WrongDetailsException e) {
            throw new NoSuchElementException("No coupons belongs to this customer in the DB");
        }
    }

    public List<Coupon> getCustomerCoupons(int categoryID) throws WrongDetailsException {
        List<CustomerVSCoupon> ownedCoupons = new ArrayList<>(customerVSCouponRepository.findByCustomerId(AuthController.customerID));
        List<Coupon> coupons = new ArrayList<>();
        for (CustomerVSCoupon c : ownedCoupons)
            coupons.add(couponRepository.findById(c.getCoupon()).orElseThrow(WrongDetailsException::new));
        for (int i = coupons.size() - 1; i >= 0; i--)
            if (coupons.get(i).getCategory().getId() != categoryID)
                coupons.remove(i);
        return coupons;
    }

    public List<Coupon> getCustomerCoupons(Double maxPrice) throws WrongDetailsException {
        List<CustomerVSCoupon> ownedCoupons = new ArrayList<>(customerVSCouponRepository.findByCustomerId(AuthController.customerID));
        List<Coupon> coupons = new ArrayList<>();
        for (CustomerVSCoupon c : ownedCoupons)
            coupons.add(couponRepository.findById(c.getCoupon()).orElseThrow(WrongDetailsException::new));
        for (int i = coupons.size() - 1; i >= 0; i--)
            if (coupons.get(i).getPrice() > maxPrice)
                coupons.remove(i);
        return coupons;
    }

    public Customer getCustomerDetails() throws NoSuchElementException, WrongDetailsException {
        try {
            return customerRepository.findById(AuthController.customerID).orElseThrow(WrongDetailsException::new);
        } catch (NoSuchElementException e) {
            throw new WrongDetailsException();
        }
    }

    public Coupon getOneCoupon(int couponID) throws NoSuchElementException, WrongDetailsException {
        try {
            return couponRepository.findById(couponID).orElseThrow(WrongDetailsException::new);
        } catch (NoSuchElementException e) {
            throw new WrongDetailsException("Wrong id try again");
        }
    }

    public List<Coupon> getAllCoupons() throws NoSuchElementException {
            List<Coupon> coupons = new ArrayList<>(couponRepository.findAll());
            if(coupons.isEmpty())
                throw new NoSuchElementException("You don't have any coupons");
            return coupons;
    }
}