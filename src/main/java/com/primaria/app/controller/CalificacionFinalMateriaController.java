package com.primaria.app.controller;

import com.primaria.app.DTO.CalificacionFinalMateriaDTO;
import com.primaria.app.DTO.CalificacionTotalAlumnoDTO;
import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.DTO.PromedioGradoCicloDTO;
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
    
    @GetMapping("/promedio/ciclo/{cicloId}")
    @Operation(summary = "RF2.9 Obtener promedio por ciclo escolar", 
               description = "Devuelve la lista de alumnos con su promedio final por ciclo escolar")
    public ResponseEntity<List<CalificacionTotalAlumnoDTO>> obtenerPromedioPorCiclo(
            @PathVariable String cicloId) {
        List<CalificacionTotalAlumnoDTO> resultado = calificacionService.getPromedioPorCiclo(cicloId);
        return ResponseEntity.ok(resultado);
    }
    
    
    @Operation(
            summary = "RF3.8 Obtener promedio general por alumno",
            description = """
                Retorna el promedio general de las materias cursadas por un alumno,
                agrupadas por grado y ciclo escolar.  
                Se calcula automáticamente el promedio de todas las materias registradas
                para ese alumno dentro de cada grado y ciclo.
                """,
            parameters = {
                @Parameter(
                    name = "alumnoId",
                    description = "ID del alumno (UUID del estudiante)",
                    example = "f9ec27d8-7a95-4964-a887-65bf763b4d5b",
                    required = true
                )
            }
        )
        @GetMapping("/promedios/{alumnoId}")
        public ResponseEntity<List<PromedioGradoCicloDTO>> obtenerPromediosPorAlumno(
                @PathVariable String alumnoId) {

            List<PromedioGradoCicloDTO> promedios = calificacionService.obtenerPromedios(alumnoId);

            if (promedios.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(promedios);
        }
}
