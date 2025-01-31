package com.example.CouponProjectServer.repositories;

import com.example.CouponProjectServer.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// this interface is for company implementation accessing the database
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    // this method checks company existence in the database using email and password
    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByNameOrEmail(String name, String email);

    // this method is used after login to init the company id using email and password
    Optional<Company> findByEmailAndPassword(String email, String password);

}
