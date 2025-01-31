package com.example.CouponProjectServer.repositories;

import com.example.CouponProjectServer.beans.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// this interface is for category implementation accessing the database
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
