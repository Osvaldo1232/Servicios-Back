package com.primaria.app.controller;

import com.primaria.app.DTO.CalificacionDTO;
import com.primaria.app.Model.Calificacion;
import com.primaria.app.Service.CalificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calificacion")
@CrossOrigin(origins = "*")
@Tag(name = "Calificaciones", description = "Endpoints para administrar calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService service;

    @PostMapping("/guardar")
    @Operation(summary = "Guardar calificación", description = "Crea una nueva calificación para un alumno y actividad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calificación creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al crear calificación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> guardar(@RequestBody CalificacionDTO dto) {
        try {
            Calificacion cal = service.guardarCalificacion(dto);
            return ResponseEntity.ok(
                    java.util.Map.of(
                            "mensaje", "Calificación creada exitosamente",
                            "idCalificacion", cal.getId()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(
                    java.util.Map.of(
                            "mensaje", "Error al crear calificación",
                            "detalle", e.getMessage()
                    )
            );
        }
    }

    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar calificaciones", description = "Filtra calificaciones por alumno, materia, trimestre o ciclo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "404", description = "No se encontraron calificaciones"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> filtrar(
            @RequestParam(required = false) String alumnoId,
            @RequestParam(required = false) String materiaId,
            @RequestParam(required = false) String trimestreId,
            @RequestParam(required = false) String cicloId
    ) {
        try {
            List<Calificacion> lista = service.filtrarCalificaciones(alumnoId, materiaId, trimestreId, cicloId);
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/promedio")
    @Operation(summary = "Promedio de calificaciones", description = "Calcula promedio de un alumno en una materia y ciclo")
    public ResponseEntity<?> promedio(
            @RequestParam String alumnoId,
            @RequestParam String materiaId,
            @RequestParam String cicloId
    ) {
        try {
            Double promedio = service.calcularPromedio(alumnoId, materiaId, cicloId);
            return ResponseEntity.ok(java.util.Map.of(
                    "alumnoId", alumnoId,
                    "materiaId", materiaId,
                    "cicloId", cicloId,
                    "promedio", promedio
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }
}
