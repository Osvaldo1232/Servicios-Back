package com.primaria.app.controller;

import com.primaria.app.DTO.AsignacionDocenteGradoGrupoDTO;
import com.primaria.app.DTO.AsignacionDocenteGradoGrupoResumenDTO;
import com.primaria.app.DTO.AsignacionGradoGrupoCicloDTO;
import com.primaria.app.Service.AsignacionDocenteGradoGrupoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/asignacion-docente")
@CrossOrigin(origins = "*")
@Tag(name = "Asignación de Docente a Grado/Grupo/Ciclo", description = "Endpoints para asignar docentes y filtrar asignaciones")
public class AsignacionDocenteGradoGrupoController {

    @Autowired
    private AsignacionDocenteGradoGrupoService service;

    @Operation(summary = "RF4.25 Guardar una asignación de docente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asignación creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o referencias no encontradas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody AsignacionDocenteGradoGrupoDTO dto) {
        try {
            String idAsignacion = service.guardarAsignacion(dto);
            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Asignación creada exitosamente",
                    "idAsignacion", idAsignacion
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(
                Map.of(
                    "mensaje", "Error al crear asignación",
                    "detalle", e.getMessage()
                )
            );
        }
    }


    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar asignaciones (resumen)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "404", description = "No se encontraron asignaciones"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> filtrarResumen(
            @RequestParam(required = false) String docenteId,
            @RequestParam(required = false) String gradoId,
            @RequestParam(required = false) String grupoId,
            @RequestParam(required = false) String cicloId
    ) {
        try {
            List<AsignacionDocenteGradoGrupoResumenDTO> resultado = service.filtrarAsignacionesResumen(docenteId, gradoId, grupoId, cicloId);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("mensaje", "Error interno del servidor", "detalle", e.getMessage()));
        }
    }
    
    
    @GetMapping("/resumen-profesor/reciente")
    @Operation(summary = "Grado, Grupo y Ciclo de la asignación más reciente de un profesor")
    public ResponseEntity<?> obtenerMasRecientePorProfesor(@RequestParam String idProfesor) {
        try {
            AsignacionGradoGrupoCicloDTO resultado = service.obtenerMasRecientePorProfesor(idProfesor);
            if (resultado == null) {
                return ResponseEntity.status(404).body(Map.of("mensaje", "No se encontraron asignaciones para el profesor"));
            }
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "mensaje", "Error interno del servidor",
                "detalle", e.getMessage()
            ));
        }
    }


}
