package com.example.dto;

import com.example.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;




public record AuthResponse
        (Usuario usuario,
        String token
        )

{}
