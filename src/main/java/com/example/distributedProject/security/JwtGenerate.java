package com.example.distributedProject.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerate {
    @Value("${dist.app.secret}")
    private String APP_SECRET;
    @Value("${dist.expires.in}")
    private long EXPIRES_IN;

    public String generateJwtToken(Authentication authentication){
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder()
                .setSubject(Integer.toString(userDetails.getUuid()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET)
                .compact();
    }

    public Integer getUserIdFromToken(String token){
        Claims claims =  (Claims) Jwts.parser()
                .setSigningKey(APP_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token süresi dolmuş: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Hatalı token formatı.");
        } catch (SignatureException e) {
            System.out.println("Token imzası geçersiz.");
        } catch (IllegalArgumentException e) {
            System.out.println("Boş token.");
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        Date expire = (Date) Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expire.before(new Date());
    }
}
