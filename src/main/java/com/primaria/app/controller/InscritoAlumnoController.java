package com.primaria.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.primaria.app.DTO.AlumnoCargaDTO;
import com.primaria.app.DTO.AlumnoInfoDTO;

import com.primaria.app.DTO.InfoAlumnoTutorDTO;
import com.primaria.app.DTO.InscritoAlumnoDTO;
import com.primaria.app.DTO.InscritoAlumnoInfoBasicaDTO;
import com.primaria.app.DTO.InscritoAlumnoRecienteDTO;
import com.primaria.app.DTO.ProfesorDTO;
import com.primaria.app.DTO.ProfesorRDTO;
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
        summary = "RF4.26 Guardar inscripción de un alumno",
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
        summary = "RF4.27 Guardar inscripciones de varios alumnos",
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
    
    
    @Operation(
            summary = " RF3.7 Obtener la inscripción más reciente de un alumno",
            description = "Devuelve el último registro de inscripción del alumno indicado, con grado, grupo, ciclo y profesor."
        )
        @GetMapping("/reciente/{alumnoId}")
        public InscritoAlumnoRecienteDTO obtenerUltimoPorAlumno(
                @Parameter(description = "UUID del alumno a consultar") 
                @PathVariable String alumnoId) {

            return inscritoAlumnoService.obtenerUltimoPorAlumno(alumnoId);
        }
    
    
    @Operation(summary = "RF4.29 Obtener información de alumno y tutor por ID del alumno")
    @GetMapping("/{idAlumno}")
    public ResponseEntity<?> obtenerInfo(@PathVariable String idAlumno) {
        try {
            InfoAlumnoTutorDTO dto = inscritoAlumnoService.obtenerInfoPorAlumno(idAlumno);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body(java.util.Map.of("mensaje", e.getMessage()));
        }
    }
    
    @Operation(
            summary = "RF2.7 Filtrar inscripciones por grado, grupo y ciclo escolar",
            description = "Devuelve una lista con información básica del alumno, filtrada por grado, grupo y ciclo escolar."
        )
        @GetMapping("/filtrarAlumnos")
        public ResponseEntity<List<InscritoAlumnoInfoBasicaDTO>> obtenerPorGradoGrupoCiclo(
                @Parameter(description = "ID del grado ") @RequestParam(required = true) String gradoId,
                @Parameter(description = "ID del grupo ") @RequestParam(required = true) String grupoId,
                @Parameter(description = "ID del ciclo escolar ") @RequestParam(required = true) String cicloId
        ) {
            List<InscritoAlumnoInfoBasicaDTO> resultado =
                    inscritoAlumnoService.obtenerPorGradoGrupoCiclo(gradoId, grupoId, cicloId);

            if (resultado.isEmpty()) {
                return ResponseEntity.noContent().build(); // HTTP 204 si no hay resultados
            }

            return ResponseEntity.ok(resultado); // HTTP 200 con la lista de inscripciones
        }
    
   
    @Operation(
            summary = "RF2.8 Obtener alumnos inscritos por ciclo escolar",
            description = "Devuelve una lista de alumnos con sus datos personales, grado, grupo y tutor, filtrados por ID del ciclo escolar."
        )
       
        @GetMapping("/alumnos/{cicloId}")
        public List<AlumnoCargaDTO> obtenerAlumnosPorCiclo(@PathVariable String cicloId) {
            return inscritoAlumnoService.obtenerAlumnosPorCiclo(cicloId);
        }
    
    @Operation(
            summary = "Obtener docente por grado, grupo y ciclo",
            description = "Devuelve el ID y nombre del docente correspondiente al grado, grupo y ciclo especificados.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Docentes encontrados"),
                @ApiResponse(responseCode = "404", description = "No se encontraron docentes")
            }
        )
        @GetMapping("/docentes")
        public ResponseEntity<List<ProfesorRDTO>> obtenerDocentePorGradoGrupoYCiclo(
                @RequestParam String gradoId,
                @RequestParam String grupoId,
                @RequestParam String cicloId) {

            List<ProfesorRDTO> docentes = inscritoAlumnoService.obtenerDocentesPorGradoGrupoYCiclo(gradoId, grupoId, cicloId);

            if (docentes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(docentes);
        }
}
