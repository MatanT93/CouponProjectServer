package com.example.CouponProjectServer.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Date;

@Component
@Order(2) // run AFTER CorsFilter
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            if (TokensManager.activeTokens.contains(token)) {
                DecodedJWT decoded = JWT.decode(token);
                // check info in token
                if (decoded.getIssuer().equals("CouponsProject") && decoded.getExpiresAt().before(new Date())) {
                    // all is well, move on
                    filterChain.doFilter(request, response); // move to next filter on the chain, if last filter send to dispatcher
                }
            }
            else {
                response.setStatus(401);
                response.getWriter().write("Unauthorized, please log in!");
            }
        } catch (Exception e) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized, please log in!");
            response.getWriter().write(response.toString());
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !(request.getServletPath().startsWith("/users/admin")
                ||request.getServletPath().startsWith("/users/company")
                ||request.getServletPath().startsWith("/users/customer"));
    }

}