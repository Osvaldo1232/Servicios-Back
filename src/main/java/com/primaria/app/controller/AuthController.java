package com.primaria.app.controller;


import com.primaria.app.DTO.LoginRequest;
import com.primaria.app.Model.Usuario;
import com.primaria.app.Service.UsuarioService;
import com.primaria.app.security.JwtUtil;
import com.primaria.app.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Autenticacion")
@Tag(name = "Autenticación", description = "Operaciones relacionadas con login y autenticación de usuarios")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(
        summary = "RF1.1 y RF1.2 Iniciar sesión",
        description = "Autentica al usuario con su email y contraseña. Devuelve un token JWT si las credenciales son válidas.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(example = "{\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6...\"}")
                )
            ),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(example = "{\"error\":\"Credenciales inválidas\"}")
                )
            )
        }
    )
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            String token = jwtUtil.generateToken(usuario.getId(), usuario.getRol().name());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "rol", usuario.getRol().name(),
                    "uuid", usuario.getId()
            ));
        } catch (BusinessException e) {
            return ResponseEntity.status(e.getCodigo() == 403 ? 403 : 401)
                                 .body(Map.of("error", e.getMessage()));
        }
    }

}