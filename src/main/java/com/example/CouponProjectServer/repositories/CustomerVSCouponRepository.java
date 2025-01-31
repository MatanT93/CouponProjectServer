package com.example.CouponProjectServer.repositories;

import com.example.CouponProjectServer.beans.CustomerVSCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// this interface is for customerVSCoupon implementation accessing the database
public interface CustomerVSCouponRepository extends JpaRepository<CustomerVSCoupon, Integer> {

    //this method delete customer from the purchase table using id
    void deleteByCustomerId(int customerID);
    //this method delete coupon from purchase table using id
    void deleteByCouponId(int couponID);
    // this method checks purchase existence in the database using customer and coupon id's
    boolean existsByCustomerIdAndCouponId(int customerID, int couponID);
    //this method gets a coupons list belongs to a specific customer id
    List<CustomerVSCoupon> findByCustomerId(int customerID);
}
