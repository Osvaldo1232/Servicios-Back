package com.primaria.app.controller;

import com.primaria.app.DTO.AsignacionDocenteGradoGrupoDTO;
import com.primaria.app.DTO.AsignacionDocenteGradoGrupoResumenDTO;
import com.primaria.app.DTO.AsignacionGradoGrupoCicloDTO;
import com.primaria.app.DTO.CicloSimpleDTO;
import com.primaria.app.DTO.ProfesorRDTO;
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

    @Operation(summary = "RF4.32:  Guardar una asignación de docente")
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

    @Operation(summary = " RF4.33 Obtener asignaciones por ciclo escolar, devuelve grado, grupo y profesor")
    @GetMapping("/por-ciclo/{idCiclo}")
    public ResponseEntity<List<AsignacionDocenteGradoGrupoResumenDTO>> obtenerPorCiclo(@PathVariable String idCiclo) {
        return ResponseEntity.ok(service.obtenerPorCiclo(idCiclo));
    }
  
    
    @GetMapping("/resumen-profesor/reciente")
    @Operation(summary = "RF2.1 Grado, Grupo y Ciclo de la asignación más reciente de un profesor")
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

    @Operation(
            summary = "Obtener ciclos por docente",
            description = "Devuelve una lista de ciclos escolares (id y nombre) donde el docente ha estado asignado."
        )
        @GetMapping("/{idDocente}/ciclos")
        public List<CicloSimpleDTO> obtenerCiclosPorDocente(
                @PathVariable String idDocente
        ) {
            return service.obtenerCiclosPorDocente(idDocente);
        }
    
    
    @Operation(summary = "RF2.5 Listar asignaciones activas de un docente por ID")
    @GetMapping("/docente-asignaciones/{idDocente}")
    public List<ProfesorRDTO> obtenerAsignacionesPorDocente(@PathVariable String idDocente) {
        return service.obtenerAsignacionesPorDocente(idDocente);
    }
    
    
    @Operation(summary = " RF2.5 Obtiene la asignación más reciente de un docente por su ID (id y value)")
    @GetMapping("/docente/{idDocente}/reciente")
    public ProfesorRDTO obtenerAsignacionMasReciente(@PathVariable String idDocente) {
        return service.obtenerAsignacionMasReReciente(idDocente);
    }
}
