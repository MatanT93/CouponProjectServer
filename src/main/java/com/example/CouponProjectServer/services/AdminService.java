package com.example.CouponProjectServer.services;

import com.example.CouponProjectServer.beans.Company;
import com.example.CouponProjectServer.beans.Coupon;
import com.example.CouponProjectServer.beans.Customer;
import com.example.CouponProjectServer.exceptions.NameAlreadyExistException;
import com.example.CouponProjectServer.exceptions.WrongDetailsException;
import com.example.CouponProjectServer.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AdminService extends ClientService {

    public AdminService(CategoryRepository categoryRepository, CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, CustomerVSCouponRepository customerVSCouponRepository) {
        super(categoryRepository, companyRepository, couponRepository, customerRepository, customerVSCouponRepository);
    }

    public boolean adminLogin(String email, String password) {return email.equals("admin@admin.com") && password.equals("admin");}

    //public boolean existsByNameOrEmail(String name, String email) {return companyRepository.existsByNameOrEmail(name, email);}

    public Company addCompany(Company company) throws NameAlreadyExistException {
        if (companyRepository.existsByNameOrEmail(company.getName(), company.getEmail()))
            throw new NameAlreadyExistException();
        else
            return companyRepository.save(company);
    }

    public Company updateCompany(int companyID, Company company) throws WrongDetailsException {
        try {
            Company companyTemp = companyRepository.findById(companyID).orElseThrow();
            companyTemp.setEmail(company.getEmail());
            companyTemp.setPassword(company.getPassword());
            return companyRepository.save(companyTemp);
        } catch (NoSuchElementException e) {
            throw new WrongDetailsException();
        }
    }

    @Transactional
    public void deleteCompany(int companyID) throws NoSuchElementException {
        try {
            List<Coupon> companyCoupons = couponRepository.findByCompanyId(companyID);
            for (Coupon coupon : companyCoupons) {
                customerVSCouponRepository.deleteByCouponId(coupon.getId());
                couponRepository.deleteById(coupon.getId());
            }
            companyRepository.deleteById(companyID);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    public List<Company> getAllCompanies() throws NoSuchElementException {
        List<Company> companies = new ArrayList<>(companyRepository.findAll());
        if (companies.isEmpty())
            throw new NoSuchElementException("No companies in the DB");
        return companies;
    }

    public Company getOneCompany(int companyID) throws WrongDetailsException {
        try {
            return companyRepository.findById(companyID).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new WrongDetailsException("Wrong id try again");
        }
    }

    public Customer addCustomer(Customer customer) throws NameAlreadyExistException {
        if (customerRepository.existsByEmail(customer.getEmail()))
            throw new NameAlreadyExistException("Email already exist");
        else
            return customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) throws WrongDetailsException {
        try {
            Customer customerTemp = customerRepository.findById(customer.getId()).orElseThrow();
            customerTemp.setFirstName(customer.getFirstName());
            customerTemp.setLastName(customer.getLastName());
            customerTemp.setEmail(customer.getEmail());
            customerTemp.setPassword(customer.getPassword());
            customerRepository.save(customerTemp);
        } catch (NoSuchElementException e) {
            throw new WrongDetailsException();

        }
    }

    @Transactional
    public void deleteCustomer(int customerID) {
        customerVSCouponRepository.deleteByCustomerId(customerID);
        customerRepository.deleteById(customerID);

    }

    public List<Customer> getAllCustomers() throws NoSuchElementException {
        List<Customer> customers = new ArrayList<>(customerRepository.findAll());
        if (customers.isEmpty())
            throw new NoSuchElementException("No customers in the DB");
        return customers;
    }

    public Customer getOneCustomer(int customerID) throws WrongDetailsException {
        try {
            return customerRepository.findById(customerID).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new WrongDetailsException("Wrong id try again");
        }
    }
}
