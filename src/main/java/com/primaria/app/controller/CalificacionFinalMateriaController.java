package com.primaria.app.controller;

import com.primaria.app.DTO.CalificacionFinalMateriaDTO;
import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.Service.CalificacionFinalMateriaService;
import com.primaria.app.Service.CalificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calificaciones-finales")
@Tag(name = "Calificaciones Finales", description = "Operaciones para gestionar calificaciones finales de materias")
public class CalificacionFinalMateriaController {

    @Autowired
    private CalificacionService calificacionServic;

    @Autowired
    private CalificacionFinalMateriaService calificacionService;

    @Operation(
            summary = "Registrar una nueva calificación final",
            description = "Crea un registro de calificación final para un alumno en una materia específica dentro de un ciclo escolar."
    )
 
    @PostMapping
    public ResponseEntity<CalificacionFinalMateriaDTO> crearCalificacion(
            @Parameter(description = "DTO con la información de la calificación final a registrar") 
            @RequestBody CalificacionFinalMateriaDTO dto) {
        CalificacionFinalMateriaDTO creada = calificacionService.crearCalificacion(dto);
        return ResponseEntity.ok(creada);
    }

    @Operation(
            summary = "Obtener una calificación final por ID",
            description = "Recupera la información de una calificación final específica usando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Calificación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionFinalMateriaDTO> obtenerCalificacion(
            @Parameter(description = "ID de la calificación final a buscar") 
            @PathVariable String id) {
        return calificacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "RF3.2: Obtener calificaciones por alumno y ciclo",
            description = "Devuelve las calificaciones de un alumno agrupadas por materia dentro de un ciclo escolar específico"
    )
   
    @GetMapping("/alumno/{alumnoId}/ciclo/{cicloId}")
    public List<MateriaCalificacionResDTO> obtenerCalificaciones(
            @Parameter(description = "ID del alumno cuyas calificaciones se desean consultar") 
            @PathVariable String alumnoId,
            @Parameter(description = "ID del ciclo escolar para filtrar las calificaciones") 
            @PathVariable String cicloId) {

        return calificacionServic.obtenerCalificacionesPorAlumnoYciclo(alumnoId, cicloId);
    }
}
