package com.primaria.app.controller;

import com.primaria.app.DTO.CalificacionFinalMateriaDTO;
import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.Service.CalificacionFinalMateriaService;
import com.primaria.app.Service.CalificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("calificaciones-finales")
@Tag(name = "Calificaciones Finales", description = "Operaciones para gestionar calificaciones finales de materias")
public class CalificacionFinalMateriaController {

	   @Autowired
	    private CalificacionService calificacionServic;
	   
    @Autowired
    private CalificacionFinalMateriaService calificacionService;

    @Operation(summary = "Registrar una nueva calificación final")
    @PostMapping
    public ResponseEntity<CalificacionFinalMateriaDTO> crearCalificacion(@RequestBody CalificacionFinalMateriaDTO dto) {
        CalificacionFinalMateriaDTO creada = calificacionService.crearCalificacion(dto);
        return ResponseEntity.ok(creada);
    }

    @Operation(summary = "Obtener una calificación final por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionFinalMateriaDTO> obtenerCalificacion(@PathVariable String id) {
        return calificacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    
    @Operation(summary = "RF3.2:  Obtener calificaciones por alumno y ciclo")
    @GetMapping("/alumno/{alumnoId}/ciclo/{cicloId}")
    public List<MateriaCalificacionResDTO> obtenerCalificaciones(
            @PathVariable String alumnoId,
            @PathVariable String cicloId) {

        return calificacionServic.obtenerCalificacionesPorAlumnoYciclo(alumnoId, cicloId);
    }
}
