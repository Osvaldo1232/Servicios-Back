package com.primaria.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.primaria.app.DTO.AlumnoInfoDTO;
import com.primaria.app.DTO.InscritoAlumnoDTO;
import com.primaria.app.DTO.InscritoAlumnoDetalleDTO;
import com.primaria.app.Model.InscritoAlumno;
import com.primaria.app.Service.InscritoAlumnoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/inscripcion")
@CrossOrigin(origins = "*")
@Tag(name = "Inscripción de Alumnos", description = "Endpoints para inscribir alumnos en grado, grupo y ciclo escolar")
public class InscritoAlumnoController {

    @Autowired
    private InscritoAlumnoService inscritoAlumnoService;

    @PostMapping("/guardar")
    @Operation(
        summary = "Guardar inscripción de un alumno",
        description = "Crea la inscripción de un alumno en un grado, grupo y ciclo escolar"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inscripción creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o entidades relacionadas no encontradas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Map<String, String> guardar(@RequestBody InscritoAlumnoDTO dto) {
        // Guardar la inscripción usando el servicio
        InscritoAlumno inscripcion = inscritoAlumnoService.guardarInscripcion(dto);

        // Preparar respuesta simple con mensaje y ID
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Inscripción creada exitosamente");
        respuesta.put("idInscripcion", inscripcion.getId());

        return respuesta;
    }
    
    
    
    @PostMapping("/guardar-masivo")
    @Operation(
        summary = "Guardar inscripciones de varios alumnos",
        description = "Permite inscribir varios alumnos en grado, grupo y ciclo escolar en un solo request"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inscripciones creadas exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o entidades relacionadas no encontradas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Map<String, Object> guardarMasivo(@RequestBody List<InscritoAlumnoDTO> dtos) {
        Map<String, Object> respuesta = new HashMap<>();
        
        List<String> idsCreados = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        for (InscritoAlumnoDTO dto : dtos) {
            try {
                InscritoAlumno inscripcion = inscritoAlumnoService.guardarInscripcion(dto);
                idsCreados.add(inscripcion.getId());
            } catch (Exception e) {
                // Guardamos el error para cada alumno fallido
                errores.add("Error al inscribir alumnoId " + dto.getAlumnoId() + ": " + e.getMessage());
            }
        }

        respuesta.put("idsCreados", idsCreados);
        respuesta.put("errores", errores);
        respuesta.put("mensaje", "Proceso completado");

        return respuesta;
    }
    
    
    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar alumnos inscritos",
               description = "Permite filtrar alumnos inscritos por grado, grupo y/o ciclo escolar. Al menos un parámetro es obligatorio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "400", description = "Debe proporcionar al menos un parámetro de búsqueda"),
            @ApiResponse(responseCode = "404", description = "No se encontraron alumnos inscritos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> filtrar(
            @RequestParam(required = false) String gradoId,
            @RequestParam(required = false) String grupoId,
            @RequestParam(required = false) String cicloId
    ) {
        try {
            List<InscritoAlumnoDetalleDTO> resultado = inscritoAlumnoService.filtrarInscripciones(gradoId, grupoId, cicloId);

            if (resultado.isEmpty()) {
                return ResponseEntity.status(404).body("No se encontraron alumnos inscritos con los filtros proporcionados.");
            }

            return ResponseEntity.ok(resultado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

    
    @Operation(
            summary = "Obtener información del alumno",
            description = "Devuelve el ciclo más reciente, grado, grupo y materias asignadas a un alumno específico"
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Información del alumno obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró información para el alumno"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @GetMapping("/{idAlumno}/info")
        public AlumnoInfoDTO obtenerInfoAlumno(@PathVariable String idAlumno) {
            try {
                return inscritoAlumnoService.obtenerInfoAlumno(idAlumno);
            } catch (RuntimeException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
        }
}
