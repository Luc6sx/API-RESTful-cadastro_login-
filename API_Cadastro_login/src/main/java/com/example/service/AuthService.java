package com.example.service;

import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final List<Usuario> usuarios = new ArrayList<>();
    private final PasswordEncoder passwordEncoder;
    public List<Usuario> ListarUsuarios;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Cadastro
    public Usuario cadastrar(RegisterRequest request) {
        if (usuarios.stream().anyMatch(u -> u.getEmail().equals(request.getEmail()))) {
            throw new RuntimeException("Email já cadastrado");
        }
        if (request.getPassword().length() < 6){
            throw new RuntimeException("senha deve ter no mínimo 6 caracteres");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setId((long) (usuarios.size() + 1));
        novoUsuario.setNome(request.getNome());
        novoUsuario.setEmail(request.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(request.getPassword()));

        usuarios.add(novoUsuario);
        return novoUsuario;
    }

    // Login
    public Usuario login(LoginRequest request) {
        Optional<Usuario> usuario = usuarios.stream()
                .filter(u -> u.getEmail().equals(request.getEmail()))
                .findFirst();

        if (usuario.isEmpty() || !passwordEncoder.matches(request.getPassword(), usuario.get().getPassword())) {
            throw new RuntimeException("Email ou senha incorretos");
        }

        return usuario.get();
    }


    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }
}