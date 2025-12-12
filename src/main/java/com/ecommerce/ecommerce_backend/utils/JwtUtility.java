package com.ecommerce.ecommerce_backend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtility {
     @Value("${SECRET_KEY}")
     private String SECRET_KEY;

     private Key getSignigKey(){

         return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
     }
     public String generateToken(String email, String role){
         return Jwts.builder()
                 .setSubject(email)
                 .claim("role", role)
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis() + (15 * 60 * 1000))) //15 minutes
                 .signWith(SignatureAlgorithm.HS256, getSignigKey())
                 .compact();
     }

     public String extractEmail(String token){
         //log.info("Extract Email is called: {}",extractAllClaims(token).getSubject());
         return extractAllClaims(token).getSubject();
     }

     public String extractRole(String token){
         return extractAllClaims(token).get("role", String.class);
     }

     public boolean isTokenValid(String token, UserDetails userDetails){
         try{
              String email = extractEmail(token);

             //log.info("Comparing token email '{}' with userDetails username '{}'",
              //       extractEmail(token), userDetails.getUsername());

             return email.equals(userDetails.getUsername())
                      && !isTokenExpired(token)
                      && isSignatureValid(token);
         } catch (ExpiredJwtException e) {
             log.info("Token is Expired !!!!");
             return false;
         }
         catch (JwtException e){
             log.info("Invalid token");
             return false;
         }
     }

     private boolean isSignatureValid(String token){
         try{
             Jwts.parserBuilder()
                     .setSigningKey(getSignigKey())
                     .build()
                     .parseClaimsJws(token);
             return true;
         } catch (Exception e) {
             log.info("Signature verification failed !!!!!");
             return false;
         }
     }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
     private Claims extractAllClaims(String token){
         return Jwts.parserBuilder()
                 .setSigningKey(getSignigKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
     }

}
