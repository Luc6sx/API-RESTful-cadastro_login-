package com.example.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

public class JTWTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JTWTokenProvider.class);
    private final Key jtwSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long jtwExpirationMs = 86400000; //24 horas

  public String generateToken(Authentication authentication){
      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + jtwExpirationMs);

      return Jwts.builder().
              setSubject(authentication.getName())
              .setIssuedAt(now)
              .setExpiration(expiryDate)
              .signWith(jtwSecret, SignatureAlgorithm.HS512)
              .compact();

  }
  public String getUsernameFromToken(String token){
      try {
          return Jwts.parserBuilder()
                  .setSigningKey(jtwSecret)
                  .build()
                  .parseClaimsJws(token)
                  .getBody()
                  .getSubject();
      } catch(JwtException ex){
          log.error("Token inválido: {}", ex.getMessage());
          throw new AuthenticationException("Token inválido") {
          };
      } catch (RuntimeException e) {
          log.error("token expirado: {}", e.getMessage());
          throw new AuthenticationException("Token expirado") {
          };
      }
  }

    public boolean validateToken(String token){
      try{
          Jwts.parserBuilder().setSigningKey(jtwSecret).build().parseClaimsJws(token);
          return true;
      } catch (Exception ex) {
          return false;
      }
    }
}
