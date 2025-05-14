package com.example.Controller;

import com.example.service.AuthService;
import com.example.model.Usuario;
import com.example.dto.*;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/auth")
public class AuthController {


    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    //cadastra usuario e manda ao RegisterRequest
    @PostMapping("/register")
    public Usuario register(@RequestBody RegisterRequest request) {
        return authService.cadastrar(request);
    }


    //verifica o login e manda ao LoginRequest
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }


    //retorna usuarios cadastrados
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return authService.ListarUsuarios;
    }


}
