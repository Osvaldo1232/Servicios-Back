

package com.primaria.app.controller;

import com.primaria.app.DTO.ActividadAlumnoDTO;
import com.primaria.app.Model.ActividadAlumno;
import com.primaria.app.Service.ActividadAlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actividad-alumno")
@CrossOrigin(origins = "*")
@Tag(name = "Actividad Alumno", description = "Endpoints para asignar y consultar actividades de alumnos")
public class ActividadAlumnoController {

    @Autowired
    private ActividadAlumnoService service;

    // 🔹 Asignar actividad a un alumno
    @PostMapping("/asignar")
    @Operation(summary = "Asignar actividad a un alumno", description = "Registra una actividad y su valor para un alumno específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad asignada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al asignar actividad"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> asignarActividad(@RequestBody ActividadAlumnoDTO dto) {
        try {
            ActividadAlumno aa = service.asignarActividad(dto);
            return ResponseEntity.ok(
                    java.util.Map.of(
                            "mensaje", "Actividad asignada correctamente",
                            "idActividadAlumno", aa.getId()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(
                    java.util.Map.of(
                            "mensaje", "Error al asignar actividad",
                            "detalle", e.getMessage()
                    )
            );
        }
    }

    // 🔹 Listar actividades de un alumno
    @GetMapping("/alumno/{alumnoId}")
    @Operation(summary = "Obtener actividades de un alumno", description = "Devuelve todas las actividades asignadas a un alumno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "404", description = "No se encontraron actividades"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> obtenerPorAlumno(@PathVariable String alumnoId) {
        try {
            List<ActividadAlumno> lista = service.obtenerPorAlumno(alumnoId);
            if (lista.isEmpty()) {
                return ResponseEntity.status(404).body("No se encontraron actividades para este alumno");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

    // 🔹 Calcular promedio de un alumno en un conjunto de actividades
    @GetMapping("/alumno/{alumnoId}/promedio")
    @Operation(summary = "Calcular promedio de un alumno", description = "Calcula el promedio de todas las actividades de un alumno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promedio calculado"),
            @ApiResponse(responseCode = "404", description = "No se encontraron actividades"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> calcularPromedio(@PathVariable String alumnoId) {
        try {
            List<ActividadAlumno> lista = service.obtenerPorAlumno(alumnoId);
            if (lista.isEmpty()) {
                return ResponseEntity.status(404).body("No se encontraron actividades para este alumno");
            }
            double promedio = service.calcularPromedio(lista);
            return ResponseEntity.ok(java.util.Map.of(
                    "alumnoId", alumnoId,
                    "promedio", promedio
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }
}
