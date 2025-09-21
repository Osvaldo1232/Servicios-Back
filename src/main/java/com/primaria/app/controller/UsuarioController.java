package com.primaria.app.controller;

import com.primaria.app.DTO.DirectorDTO;
import com.primaria.app.DTO.EstudianteDTO;
import com.primaria.app.DTO.ProfesorDTO;
import com.primaria.app.Model.*;
import com.primaria.app.Service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones para registrar usuarios de distintos roles")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/estudiante")
    @Operation(summary = "Registrar Estudiante")
    public ResponseEntity<?> registrarEstudiante(@RequestBody EstudianteDTO dto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        estudiante.setEmail(dto.getEmail());
        estudiante.setPassword(dto.getPassword());
        estudiante.setMatricula(dto.getMatricula());
        estudiante.setCurp(dto.getCurp());
        estudiante.setRol(Rol.ESTUDIANTE);
        usuarioService.save(estudiante);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Estudiante registrado exitosamente");
        response.put("id", estudiante.getId());

        return ResponseEntity.ok(response);
        
        
    }

    @PostMapping("/profesor")
    @Operation(summary = "Registrar Profesor")
    public ResponseEntity<?> registrarProfesor(@RequestBody ProfesorDTO dto) {
        Profesor profesor = new Profesor();
        profesor.setNombre(dto.getNombre());
        profesor.setEmail(dto.getEmail());
        profesor.setPassword(dto.getPassword());
       
        profesor.setRol(Rol.PROFESOR);
        usuarioService.save(profesor);
        return ResponseEntity.ok("Profesor registrado exitosamente");
    }

    @PostMapping("/director")
    @Operation(summary = "Registrar Director")
    public ResponseEntity<?> registrarDirector(@RequestBody DirectorDTO dto) {
        Director director = new Director();
        director.setNombre(dto.getNombre());
        director.setEmail(dto.getEmail());
        director.setPassword(dto.getPassword());
        director.setDepartamento(dto.getDepartamento());
        director.setRol(Rol.DIRECTOR);
        usuarioService.save(director);
        return ResponseEntity.ok("Director registrado exitosamente");
    }
    
    @Operation(summary = "Obtener usuario por ID con detalles según el rol")
    @GetMapping("/BuscarUsuario/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable String id) {
        Object usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }
}
