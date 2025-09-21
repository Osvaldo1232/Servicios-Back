package com.primaria.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.primaria.app.DTO.EstudianteDTO;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Service.EstudianteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/alumnos")
@Tag(name = "alumnos", description = "Operaciones para registrar usuarios de distintos roles")
public class AlumnoController {

    private final EstudianteService alumnoService;

    public AlumnoController(EstudianteService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Obtener  Estudiante")
    public ResponseEntity<?> getAlumnoPorUsuarioId(@PathVariable String id) {
        return alumnoService.findById(id)
                .map(alumno -> ResponseEntity.ok().body(alumno))
                .orElse(ResponseEntity.notFound().build());
    }
    
    
    @GetMapping("/todosestudiantes")
    @Operation(summary = "Obtener todos los estudiantes")
    public List<EstudianteDTO> obtenerTodosEstudiantes() {
        return alumnoService.listarTodos();
    }
    @Operation(summary = "Buscar estudiante por matrícula")
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<?> buscarPorMatricula(@PathVariable String matricula) {
        Estudiante estudiante = alumnoService.buscarPorMatricula(matricula);
        return ResponseEntity.ok(estudiante);
    }

    @Operation(summary = "Eliminar estudiante por matrícula")
    @DeleteMapping("/matricula/{matricula}")
    public ResponseEntity<?> eliminarPorMatricula(@PathVariable String matricula) {
    	alumnoService.eliminarPorMatricula(matricula);
        return ResponseEntity.ok("Estudiante eliminado correctamente");
    }
  
  
}
