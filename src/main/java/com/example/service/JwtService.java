package com.example.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService
{
    @Value("${jwt.secret}")// chave secreta (em application.properties)
    private  String secret;
    @Value("${jwt.expiration}") // Tempo de expiração (ex: 3600000ms / 1h)
    private  Long expiration;

    //gerar um token JWT
    public  String generateToken(String email)
    {

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes()); // cria chave de assinatura
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + expiration)) // Expiração
                .signWith(key) //assina Token
                .compact(); // converte para String

    }

    //valida token
    public boolean validadeToken(String token)
    {
        try
        {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true; // token valido




        }catch (Exception e)
        {

            return false;// token invalido

        }


    }

    // extrai email do token
    public String getEmailFromToken(String token)
    {

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build().parseSignedClaims(token)
                .getPayload()
                .getSubject(); // retorna o email

    }

}
