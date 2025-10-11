package com.primaria.app.controller;

import com.primaria.app.DTO.ActividadDTO;
import com.primaria.app.DTO.ActividadResumenDTO;
import com.primaria.app.Model.Actividad;
import com.primaria.app.Service.ActividadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actividad")
@CrossOrigin(origins = "*")
@Tag(name = "Actividades", description = "Endpoints para administrar actividades")
public class ActividadController {

    @Autowired
    private ActividadService service;

    @PostMapping("/guardar")
    @Operation(summary = "Guardar actividad", description = "Crea una nueva actividad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al crear actividad"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> guardar(@RequestBody ActividadDTO dto) {
        try {
            String id = service.guardarActividad(dto);
            return ResponseEntity.ok(
                    java.util.Map.of(
                            "mensaje", "Actividad creada exitosamente",
                            "idActividad", id
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(400).body(
                    java.util.Map.of(
                            "mensaje", "Error al crear actividad",
                            "detalle", e.getMessage()
                    )
            );
        }
    }

    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar actividades", description = "Filtra actividades por docente, asignación, trimestre o tipo de evaluación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "404", description = "No se encontraron actividades"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> filtrar(
            @RequestParam(required = false) String docenteId,
            @RequestParam(required = false) String asignacionId,
            @RequestParam(required = false) String trimestreId,
            @RequestParam(required = false) String tipoEvaluacionId
    ) {
        try {
            List<Actividad> lista = service.filtrarActividades(docenteId, asignacionId, trimestreId, tipoEvaluacionId);

            List<ActividadResumenDTO> resumen = lista.stream().map(a -> new ActividadResumenDTO(
                    a.getId(),
                    a.getNombre(),
                    a.getFecha(),
                    a.getValor(),
                    a.getDocente() != null ? a.getDocente().getId() : "",
                    a.getDocente() != null ? a.getDocente().getNombre() + " " + a.getDocente().getApellidos() : "",
                    a.getAsignacionMateriaGrado() != null ? a.getAsignacionMateriaGrado().getGrado().getNombre() : "",
                    a.getAsignacionMateriaGrado() != null ? a.getAsignacionMateriaGrado().getMateria().getNombre() : ""
            )).collect(Collectors.toList());

            return ResponseEntity.ok(resumen);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }
}
