package com.example.CouponProjectServer;

import com.example.CouponProjectServer.beans.*;
import com.example.CouponProjectServer.daily.CouponExpirationDailyJob;
import com.example.CouponProjectServer.repositories.*;
import com.example.CouponProjectServer.services.AdminService;
import com.example.CouponProjectServer.services.CompanyService;
import com.example.CouponProjectServer.services.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.sql.Date;

@SpringBootApplication
@CrossOrigin
public class CouponProjectServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(CouponProjectServerApplication.class, args);

		AdminService adminService = ctx.getBean(AdminService.class);
		CompanyService companyService = ctx.getBean(CompanyService.class);
		CustomerService customerService = ctx.getBean(CustomerService.class);
		CouponExpirationDailyJob dailyJob = ctx.getBean(CouponExpirationDailyJob.class);

		Thread t = new Thread(dailyJob);
		t.start();

		CategoryRepository categoryRepository = ctx.getBean(CategoryRepository.class);
		CustomerRepository customerRepository = ctx.getBean(CustomerRepository.class);
		CompanyRepository companyRepository = ctx.getBean(CompanyRepository.class);
		CouponRepository couponRepository = ctx.getBean(CouponRepository.class);
		CustomerVSCouponRepository customerVSCouponRepository = ctx.getBean(CustomerVSCouponRepository.class);


		if (categoryRepository.findAll().isEmpty()) {
			Category category = new Category("Food");
			categoryRepository.save(category);
			category = new Category("Fashion");
			categoryRepository.save(category);
			category = new Category("Cinema");
			categoryRepository.save(category);
			category = new Category("Spa");
			categoryRepository.save(category);
			category = new Category("Tech");
			categoryRepository.save(category);
			category = new Category("Sport");
			categoryRepository.save(category);
		}

		if (customerRepository.findAll().isEmpty()) {
			Customer customer = new Customer("customer 1a", "customer1b", "customer1@customer.com", "123456");
			customerRepository.save(customer);
			customer = new Customer("customer 2a", "customer 2b", "customer2@customer.com", "126324");
			customerRepository.save(customer);
			customer = new Customer("customer 3a", "customer 3b", "customer3@customer.com", "112233");
			customerRepository.save(customer);
			customer = new Customer("customer 4a", "customer 4b", "customer4@customer.com", "102030");
			customerRepository.save(customer);

		}

		if (companyRepository.findAll().isEmpty()) {
			Company company = new Company("amazon", "amazon@amazon.com", "1234");
			companyRepository.save(company);
			company = new Company("Aliexpress", "ali@buy.com", "12345");
			companyRepository.save(company);
			company = new Company("Ebay", "ebay@ebay.com", "123456");
			companyRepository.save(company);
			company = new Company("Facebook", "facebook@face.com", "1234566");
			companyRepository.save(company);
			company = new Company("MatanTech", "matan@tech.com", "112233");
			companyRepository.save(company);
		}

		if (couponRepository.findAll().isEmpty()) {
			Coupon coupon = new Coupon(companyRepository.findById(1).orElseThrow(), categoryRepository.findById(1).orElseThrow(), "test1", "test1", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-21"), 2, 20, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(1).orElseThrow(), categoryRepository.findById(2).orElseThrow(), "test2", "test2", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-24"), 4, 10, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(1).orElseThrow(), categoryRepository.findById(3).orElseThrow(), "test3", "test3", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-26"), 3, 15, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(2).orElseThrow(), categoryRepository.findById(4).orElseThrow(), "test4", "test4", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-28"), 5, 20, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(2).orElseThrow(), categoryRepository.findById(5).orElseThrow(), "test5", "test5", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-23"), 6, 25, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(2).orElseThrow(), categoryRepository.findById(6).orElseThrow(), "test6", "test6", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-25"), 7, 30, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(3).orElseThrow(), categoryRepository.findById(2).orElseThrow(), "test7", "test7", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-27"), 1, 35, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(3).orElseThrow(), categoryRepository.findById(4).orElseThrow(), "test8", "test8", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-22"), 32, 13, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(3).orElseThrow(), categoryRepository.findById(6).orElseThrow(), "test9", "test9", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-22"), 4, 55, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(4).orElseThrow(), categoryRepository.findById(1).orElseThrow(), "test10", "test10", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-22"), 9, 67, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(4).orElseThrow(), categoryRepository.findById(3).orElseThrow(), "test11", "test11", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-22"), 35, 6, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(4).orElseThrow(), categoryRepository.findById(5).orElseThrow(), "test12", "test12", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-22"), 18, 80, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(5).orElseThrow(), categoryRepository.findById(4).orElseThrow(), "test13", "test13", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-22"), 15, 50, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(5).orElseThrow(), categoryRepository.findById(6).orElseThrow(), "test14", "test14", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-22"), 7, 43, "");
			couponRepository.save(coupon);
			coupon = new Coupon(companyRepository.findById(5).orElseThrow(), categoryRepository.findById(5).orElseThrow(), "test15", "test15", Date.valueOf("2025-01-18"), Date.valueOf("2024-02-22"), 10, 67, "");
			couponRepository.save(coupon);


		}

		if (customerVSCouponRepository.findAll().isEmpty()) {
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(1).orElseThrow(), couponRepository.findById(2).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(1).orElseThrow(), couponRepository.findById(7).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(1).orElseThrow(), couponRepository.findById(8).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(1).orElseThrow(), couponRepository.findById(4).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(2).orElseThrow(), couponRepository.findById(8).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(2).orElseThrow(), couponRepository.findById(6).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(2).orElseThrow(), couponRepository.findById(5).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(2).orElseThrow(), couponRepository.findById(1).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(3).orElseThrow(), couponRepository.findById(2).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(3).orElseThrow(), couponRepository.findById(3).orElseThrow()));
			customerVSCouponRepository.save(new CustomerVSCoupon(customerRepository.findById(3).orElseThrow(), couponRepository.findById(4).orElseThrow()));
		}

	}

}
