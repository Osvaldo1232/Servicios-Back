package com.primaria.app.controller;

import com.primaria.app.DTO.CalificacionFinalDTO;
import com.primaria.app.DTO.MensajeDTO;
import com.primaria.app.Model.Calificacion_final;
import com.primaria.app.Service.CalificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.primaria.app.DTO.AlumnoCalificacionesDTO;
import com.primaria.app.DTO.FiltroCalificacionesDTO;

@RestController
@RequestMapping("/calificaciones")
@Tag(name = "Calificaciones", description = "API para asignar y consultar calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @PostMapping("/asignar")
    @Operation(summary = "Asignar calificación", description = "Asigna una calificación a un alumno en una materia y trimestre específico")
    public ResponseEntity<MensajeDTO> asignarCalificacion(@Valid @RequestBody CalificacionFinalDTO dto) {
        calificacionService.asignarCalificacion(dto); // Solo guardamos
        return ResponseEntity.ok(new MensajeDTO("Registro exitoso"));
    }


    @Operation(summary = "Obtener calificación por ID", description = "Obtiene una calificación por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Calificacion_final> obtenerCalificacion(@PathVariable String id) {
        return calificacionService
                .buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    

    @Operation(summary = "Obtener calificación por ID", description = "Filtros para obtener las calificaciones (ciclo, grado, grupo)")
    @PostMapping("/grupo")
    public ResponseEntity<List<AlumnoCalificacionesDTO>> listarPorGrupo(@RequestBody FiltroCalificacionesDTO filtro) {
        List<AlumnoCalificacionesDTO> lista = calificacionService.listarCalificacionesPorGrupo(filtro);
        return ResponseEntity.ok(lista);
    }
}
