package com.example.CouponProjectServer.repositories;

import com.example.CouponProjectServer.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// this interface is for company implementation accessing the database
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    //this method gets a coupons list belongs to a specific company using company id
    List<Coupon> findByCompanyId(int companyID);

    //this method checks for coupons existence using coupon title and company id
    boolean existsByTitleAndCompanyId(String title, int companyID);

}
