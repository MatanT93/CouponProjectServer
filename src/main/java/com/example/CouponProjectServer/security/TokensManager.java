package com.example.CouponProjectServer.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.CouponProjectServer.beans.ClientType;
import com.example.CouponProjectServer.beans.Company;
import com.example.CouponProjectServer.beans.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TokensManager {

    public static final List<String> activeTokens = new ArrayList<>();
    private String token = "";
    public TokensManager() {}

    public String createToken(Customer customer, ClientType type) {
        Date expires = new Date();
        expires.setTime(expires.getTime()+1000*60*30);
        token = JWT.create()
                .withIssuer("CouponsProject")
                .withIssuedAt(new Date())
                .withClaim("username", customer.getEmail())
                .withClaim("name", customer.getFirstName() + " " + customer.getLastName())
                .withClaim("role", type.toString())
                .withExpiresAt(expires)
                .sign(Algorithm.none());
        activeTokens.add(token);
        return token;
    }


    public String createToken(Company company, ClientType type) {
        Date expires = new Date();
        expires.setTime(expires.getTime()+1000*60*30);
        token = JWT.create()
                .withIssuer("CouponsProject")
                .withIssuedAt(new Date())
                .withClaim("username", company.getEmail())
                .withClaim("name", company.getName())
                .withClaim("role", type.toString())
                .withExpiresAt(expires)
                .sign(Algorithm.none());
        activeTokens.add(token);
        return token;
    }

    public String createToken(ClientType type) {
        Date expires = new Date();
        expires.setTime(expires.getTime()+1000*60*30);
        token = JWT.create()
                .withIssuer("CouponsProject")
                .withIssuedAt(new Date())
                .withClaim("username", "admin@admin.com")
                .withClaim("role", type.toString())
                .withExpiresAt(expires)
                .sign(Algorithm.none());
        activeTokens.add(token);
        return token;
    }
}
