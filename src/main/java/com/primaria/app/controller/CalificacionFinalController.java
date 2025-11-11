package com.primaria.app.controller;

import com.primaria.app.DTO.CalificacionAlumnoDTO;
import com.primaria.app.DTO.CalificacionFinalDTO;
import com.primaria.app.DTO.MensajeDTO;
import com.primaria.app.Service.CalificacionAlumnosGradoService;
import com.primaria.app.Service.CalificacionServiceAuto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calificaciones")
@Tag(name = "Calificaciones Finales", description = "Controlador para asignar y administrar calificaciones finales de los alumnos")
public class CalificacionFinalController {

    @Autowired
    private CalificacionServiceAuto calificacionServiceAuto;

    @Autowired
    private CalificacionAlumnosGradoService calificacionAlumnosGradoService;

    
    /**
     * 🧾 Asignar una sola calificación (crear o actualizar)
     */
    @PostMapping("/asignaruna")
    @Operation(
        summary = "RF2.4 y RF2.6 - Asignar una calificación",
        description = "Asigna o actualiza una calificación de un alumno en una materia y trimestre específico")
    public ResponseEntity<MensajeDTO> asignarCalificacion(@Valid @RequestBody CalificacionFinalDTO dto) {
        try {
            calificacionServiceAuto.asignarCalificacion(dto);
            return ResponseEntity.ok(new MensajeDTO("Registro exitoso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MensajeDTO(e.getMessage()));
        }
    }

    /**
     * 📋 Asignar varias calificaciones en una sola operación
     */
    @PostMapping("/asignar-multiples")
    @Operation(
        summary = "RF2.4 y RF2.6 - Asignar múltiples calificaciones",
        description = "Permite asignar varias calificaciones a distintos alumnos, materias o trimestres en una sola petición"
    )
    public ResponseEntity<MensajeDTO> asignarCalificaciones(
            @Valid @RequestBody
            @ArraySchema(schema = @Schema(implementation = CalificacionFinalDTO.class))
            List<CalificacionFinalDTO> calificaciones) {
        try {
            calificacionServiceAuto.asignarCalificaciones(calificaciones);
            return ResponseEntity.ok(new MensajeDTO("Registros exitosos"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MensajeDTO(e.getMessage()));
        }
    }

    @Operation(
            summary = "RF2.5 Obtener calificaciones por grado",
            description = "Devuelve una lista de alumnos con sus calificaciones por trimestre y promedio final, filtrando por ciclo, grado y materia."
    )
    @GetMapping("/por-grado")
    public ResponseEntity<List<CalificacionAlumnoDTO>> obtenerCalificacionesPorGrado(
            @Parameter(description = "ID del ciclo escolar", example = "8f6a92d4-6e3f-4f37-9d19-55ef0e7a6a61")
            @RequestParam String idCiclo,

            @Parameter(description = "ID del grado", example = "080d96aa-ba67-11f0-b80d-b75daa3f3a69")
            @RequestParam String idGrado,

            @Parameter(description = "ID de la materia", example = "77646c35-ba9e-11f0-b80d-b75daa3f3a69")
            @RequestParam String idMateria
    ) {
        List<CalificacionAlumnoDTO> resultados = calificacionAlumnosGradoService.obtenerCalificaciones(idCiclo, idGrado, idMateria);
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultados);
    }
}
