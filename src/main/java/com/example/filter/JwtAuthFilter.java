package com.example.filter;

import com.example.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtAuthFilter extends OncePerRequestFilter
{

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal
    (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException
    {
        //pega token do cabeçalho "Authorization"
        String token = request.getHeader("Authorization");

        if(token != null && token.startsWith("Bearer "))
        {

            token = token.substring(7); // remove "Bearer "

        }


        // valida o token
        if (jwtService.validadeToken(token))
        {

            String email = jwtService.getEmailFromToken(token);

            // autentica o usuario no spring security
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(email, null, List.of())
            );

        }

        // continua o fluxo de requisição
        filterChain.doFilter(request, response);


    }



}
