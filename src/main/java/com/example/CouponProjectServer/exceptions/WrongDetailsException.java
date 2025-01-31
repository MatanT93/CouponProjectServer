package com.example.CouponProjectServer.exceptions;

public class WrongDetailsException extends Throwable {

    public WrongDetailsException() {super("Wrong details try again");}

    public WrongDetailsException(String m) {
        super(m);
    }
}
