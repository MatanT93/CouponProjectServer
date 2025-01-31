package com.example.CouponProjectServer.exceptions;

public class CouponOutOfStockException extends Exception {

    public CouponOutOfStockException() {super("Cannot purchase coupon - out of stock");}

    public CouponOutOfStockException(String m) {super(m);}
}
