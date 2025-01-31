package com.example.CouponProjectServer.beans;

import jakarta.persistence.*;

// this class represent purchased coupon object using two foreign keys from coupons and customers tables
@Entity
@Table(name="customers_vs_coupons")

public class CustomerVSCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "smallint UNSIGNED not null")
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false, referencedColumnName = "coupon_id")
    private Coupon coupon;

    public CustomerVSCoupon() {}

    //used for insert
    public CustomerVSCoupon(Customer customer, Coupon coupon) {
        this.customer = customer;
        this.coupon = coupon;
    }

    public CustomerVSCoupon(int id, Customer customer, Coupon coupon) {
        this.id = id;
        this.customer = customer;
        this.coupon = coupon;
    }

    public int getId() {
        return id;
    }

    public int getCustomer() {
        return customer.getId();
    }

    public int getCoupon() {return coupon.getId();}

    @Override
    public String toString() {return "CustomerVSCoupon id : " + id + " customer id : " + customer.getId() + " coupon id : " + coupon.getId();}
}
