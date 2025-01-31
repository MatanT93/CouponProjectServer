package com.example.CouponProjectServer.services;

import com.example.CouponProjectServer.beans.Category;
import com.example.CouponProjectServer.beans.Company;
import com.example.CouponProjectServer.beans.Coupon;
import com.example.CouponProjectServer.controllers.AuthController;
import com.example.CouponProjectServer.exceptions.NameAlreadyExistException;
import com.example.CouponProjectServer.exceptions.WrongDetailsException;
import com.example.CouponProjectServer.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CompanyService extends ClientService{

    public CompanyService(CategoryRepository categoryRepository, CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, CustomerVSCouponRepository customerVSCouponRepository) {
        super(categoryRepository, companyRepository, couponRepository, customerRepository, customerVSCouponRepository);
    }

    public boolean existsByEmailAndPassword(String email, String password) {return companyRepository.existsByEmailAndPassword(email, password);
    }

    public Optional<Company> findByEmailAndPassword(String email, String password) {return companyRepository.findByEmailAndPassword(email, password);}

    public Coupon addCoupon(Coupon coupon) throws NameAlreadyExistException {
        if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), coupon.getCompany().getId()))
            throw new NameAlreadyExistException();
        else
            return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) throws WrongDetailsException, NoSuchElementException {
        try {
            Coupon couponTemp = couponRepository.findById(coupon.getId()).orElseThrow();
            if(couponTemp.getCompany().getId() != AuthController.companyID)
                throw new WrongDetailsException("Coupon doesn't belong to company");
            else {
                couponTemp.setAmount(coupon.getAmount());
                couponTemp.setPrice(coupon.getPrice());
                couponTemp.setCategory(categoryRepository.findById(coupon.getCategory().getId()).orElseThrow());
                couponTemp.setTitle(coupon.getTitle());
                couponTemp.setDescription(coupon.getDescription());
                couponTemp.setImage(coupon.getImage());
                couponTemp.setStartDate(coupon.getStartDate());
                couponTemp.setEndDate(coupon.getEndDate());
                return couponRepository.save(couponTemp);
            }
        } catch (WrongDetailsException | NoSuchElementException e) {
            throw new WrongDetailsException(e.getMessage());
        }
    }

    @Transactional
    public void deleteCoupon(int couponID) throws WrongDetailsException {
        if (couponRepository.findById(couponID).orElseThrow().getCompany().getId() != AuthController.companyID)
            throw new WrongDetailsException("Coupon doesn't belong to company");
        else {
            customerVSCouponRepository.deleteByCouponId(couponID);
            couponRepository.deleteById(couponID);
        }
    }

    public List<Coupon> getCompanyCoupons() throws NoSuchElementException {
        List<Coupon> companyCoupons = couponRepository.findByCompanyId(AuthController.companyID);
        if(companyCoupons.isEmpty())
            throw new NoSuchElementException("No coupons belongs to this company in the DB");
        return companyCoupons;
    }

    public List<Coupon> getCompanyCoupons(int categoryID) throws NoSuchElementException {
        List<Coupon> coupons = couponRepository.findByCompanyId(AuthController.companyID);
        for (int i = coupons.size() - 1; i >= 0; i--)
            if (coupons.get(i).getCategory().getId() != categoryID)
                coupons.remove(i);
        return coupons;
    }

    public List<Coupon> getCompanyCoupons(Double maxPrice) {
        List<Coupon> coupons = couponRepository.findByCompanyId(AuthController.companyID);
        for (int i = coupons.size() - 1; i >= 0; i--)
            if (coupons.get(i).getPrice() > maxPrice)
                coupons.remove(i);
        return coupons;
    }

    public Company getCompanyDetails() throws WrongDetailsException {
        return companyRepository.findById(AuthController.companyID).orElseThrow(WrongDetailsException::new);
    }

    public Coupon getOneCoupon(int couponID) throws WrongDetailsException {
            return couponRepository.findById(couponID).orElseThrow(WrongDetailsException::new);
    }

    public List<Category> getAllCategories() throws NoSuchElementException {
        return categoryRepository.findAll();
    }
}
