package com.primaria.app.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.primaria.app.Service.EstudianteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/alumnos")
@Tag(name = "Usuarios", description = "Operaciones para registrar usuarios de distintos roles")
public class AlumnoController {

    private final EstudianteService alumnoService;

    public AlumnoController(EstudianteService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Obtener  Estudiante")
    public ResponseEntity<?> getAlumnoPorUsuarioId(@PathVariable UUID id) {
        return alumnoService.findById(id)
                .map(alumno -> ResponseEntity.ok().body(alumno))
                .orElse(ResponseEntity.notFound().build());
    }
}
