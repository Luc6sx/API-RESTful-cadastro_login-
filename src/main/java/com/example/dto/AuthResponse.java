package com.example.dto;

import com.example.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor


public class AuthResponse
{

Usuario usuario;
String token;


    public AuthResponse(Usuario usuario, String token) {
    }
}
