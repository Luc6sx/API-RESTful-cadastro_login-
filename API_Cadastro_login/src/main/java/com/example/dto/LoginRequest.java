package com.example.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;

    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.senha;
    }
}