package com.example.CouponProjectServer.repositories;

import com.example.CouponProjectServer.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// this interface is for customer implementation accessing the database
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // this method checks customer existence in the database using email and password
    boolean existsByEmailAndPassword(String email, String password) /*throws SQLException*/;

    // this method checks customer existence in the database using email
    boolean existsByEmail(String email);

    // this method is used after login to init the customer id using email and password
    Optional<Customer> findByEmailAndPassword(String email, String password);


}
