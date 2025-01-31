package com.example.CouponProjectServer.services;

import com.example.CouponProjectServer.repositories.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class ClientService {

    protected static CategoryRepository categoryRepository;
    protected static CompanyRepository companyRepository;
    protected static CouponRepository couponRepository;
    protected static CustomerRepository customerRepository;
    protected static CustomerVSCouponRepository customerVSCouponRepository;

    public ClientService(CategoryRepository categoryRepository, CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, CustomerVSCouponRepository customerVSCouponRepository) {
        ClientService.categoryRepository = categoryRepository;
        ClientService.companyRepository = companyRepository;
        ClientService.couponRepository = couponRepository;
        ClientService.customerRepository = customerRepository;
        ClientService.customerVSCouponRepository = customerVSCouponRepository;
    }
}
