package com.example.CouponProjectServer.exceptions;

public class NameAlreadyExistException extends Exception {

    public NameAlreadyExistException() {super("Name already exist");}

    public NameAlreadyExistException(String m) {super(m);}
}
