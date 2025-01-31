package com.example.CouponProjectServer.daily;

import com.example.CouponProjectServer.beans.Coupon;
import com.example.CouponProjectServer.repositories.CouponRepository;
import com.example.CouponProjectServer.repositories.CustomerVSCouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Thread.sleep;

// this class represent a secondary thread running to delete expired coupons from the db
@Component
public class CouponExpirationDailyJob implements Runnable {

    @Autowired
    private final CouponRepository couponRepository;
    @Autowired
    private final CustomerVSCouponRepository customerVSCouponRepository;

    private boolean quit;

    public CouponExpirationDailyJob(CouponRepository couponRepository, CustomerVSCouponRepository customerVSCouponRepository) {
        this.couponRepository = couponRepository;
        this.customerVSCouponRepository = customerVSCouponRepository;
        this.quit = false;
    }

    @Override
    @Transactional
    public void run() {
        while (!quit) {
            try {
                List<Coupon> coupons = couponRepository.findAll();
                for (int i = coupons.size() - 1; i >= 0; i--) {
                    LocalDate localDate = coupons.get(i).getEndDate().toLocalDate();
                    if(localDate.isBefore(LocalDate.now())) {
                        int id = coupons.get(i).getId();
                        customerVSCouponRepository.deleteByCouponId(id);
                        couponRepository.deleteById(id);
                    }
                }
                sleep(1000 * 60); // sleep(1000 * 60 * 60 * 24); for 24-hour sleep
            } catch (InterruptedException e) {throw new RuntimeException(e);}
        }
    }

    public void stop() {quit = true;}
}
