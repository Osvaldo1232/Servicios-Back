package com.primaria.app.controller;

import com.primaria.app.DTO.AlumnoMateriasDTO;
import com.primaria.app.DTO.CalificacionTotalAlumnoDTO;
import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.DTO.PromedioCampoDTO;
import com.primaria.app.DTO.PromedioGradoCicloDTO;
import com.primaria.app.DTO.ReporteAlumnoDTO;
import com.primaria.app.DTO.ReprobadosDTO;
import com.primaria.app.DTO.ResumenCalificacionesAsignacionDTO;
import com.primaria.app.Service.CalificacionFinalMateriaService;
import com.primaria.app.Service.CalificacionService;
import com.primaria.app.Service.ReporteAlumnoService;
import com.primaria.app.Service.ReprobadosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calificaciones-finales")
@Tag(name = "Calificaciones Finales", description = "Operaciones para gestionar calificaciones finales de materias")
public class CalificacionFinalMateriaController {
	@Autowired
	 private  ReprobadosService reprobadosService;
	@Autowired
	private ReporteAlumnoService reporteAlumnoService;
    @Autowired
    private CalificacionService calificacionServic;

    @Autowired
    private CalificacionFinalMateriaService calificacionService;    
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
            summary = " Obtener promedio general por alumno",
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
    @Operation(
            summary = "RF3.4 Obtener promedios por campo formativo",
            description = "Devuelve el promedio de calificaciones agrupado por campo formativo para un ciclo escolar específico."
        )
        @GetMapping("/promedio-campos/{idCiclo}")
        public List<PromedioCampoDTO> obtenerPromediosPorCampo(
                @Parameter(description = "ID del ciclo escolar", example = "8f6a92d4-6e3f-4f37-9d19-55ef0e7a6a61")
                @PathVariable String idCiclo) {
            return calificacionService.obtenerPromediosPorCampo(idCiclo);
        }
        // 🔹 Obtener promedio general
        @Operation(
            summary = "RF3.4 Obtener promedio general",
            description = "Devuelve el promedio general de todas las materias cursadas en un ciclo escolar específico."
        )
        @GetMapping("/promedio-general/{idCiclo}")
        public Double obtenerPromedioGeneral(
                @Parameter(description = "ID del ciclo escolar", example = "8f6a92d4-6e3f-4f37-9d19-55ef0e7a6a61")
                @PathVariable String idCiclo) {
            return calificacionService.obtenerPromedioGeneral(idCiclo);
        }
        

        @Operation(
            summary = "RF2.10 Obtener reporte del alumno por ciclo",
            description = "Devuelve los promedios por campo formativo, por trimestre y el promedio general del alumno en un ciclo escolar específico."
        )
        @GetMapping("/por-ciclo")
        public ResponseEntity<ReporteAlumnoDTO> obtenerReportePorAlumno(
                @Parameter(description = "ID del alumno", example = "f9ec27d8-7a95-4964-a887-65bf763b4d5b") @RequestParam String idAlumno,
                @Parameter(description = "ID del ciclo escolar", example = "8f6a92d4-6e3f-4f37-9d19-55ef0e7a6a61") @RequestParam String idCiclo
        ) {
            ReporteAlumnoDTO reporte = reporteAlumnoService.obtenerReportePorAlumno(idAlumno, idCiclo);
            return ResponseEntity.ok(reporte);
        }
        
        
        @Operation(
                summary = " RF3.8: Obtener calificaciones finales por alumno",
                description = "Devuelve las materias, campo formativo, grado y promedios agrupados por grado del alumno."
            )
            @GetMapping("/por-alumno/{idAlumno}")
            public ResponseEntity<Map<String, Object>> obtenerPromediosPorAlumnos(
                    @PathVariable String idAlumno) {
                Map<String, Object> response = calificacionService.obtenerPromediosPorAlumno(idAlumno);
                return ResponseEntity.ok(response);
            }
        
        
        @Operation(
                summary = "RF4.35 Obtener alumnos con materias reprobadas (<6) por asignación",
                description = "Devuelve un listado de alumnos agrupados con sus materias donde obtuvieron promedio menor a 6. "
                        + "Filtra usando el ID de asignación docente-grado-grupo (id_asignacion)."
        )
        @GetMapping("reprobados/{idAsignacion}")
        public List<ReprobadosDTO> obtenerReprobados(
                @Parameter(
                        description = "ID de la asignación docente-grado-grupo",
                        example = "b7a21b63-f94d-401b-8330-b9d32e12e83e",
                        required = true
                )
                @PathVariable String idAsignacion) {

            return reprobadosService.obtenerReprobadosPorAsignacion(idAsignacion);
        }
        
        
        
        
        @GetMapping("/asignacion/{idAsignacion}")
        @Operation(
            summary = "  RF4.36 Generar estadísticas por asignación",
            description = """
                          Calcula:
                          - Alumnos inscritos  
                          - Promedio final por alumno (promedio de sus materias)  
                          - Total aprobados (>= 6)  
                          - Total reprobados (< 6)  
                          - Lista de alumnos con su promedio  
                          """
        )
        public ResponseEntity<ResumenCalificacionesAsignacionDTO> generarEstadisticas(
                @PathVariable String idAsignacion) {

            ResumenCalificacionesAsignacionDTO dto =
            		reprobadosService.generarEstadisticas(idAsignacion);

            return ResponseEntity.ok(dto);
        }
        
        
        @GetMapping("/asignaciones/{idAsignacion}")
        @Operation(
                summary = "RF4.37 Obtener materias y calificaciones agrupadas por alumno",
                description = """
                    Devuelve todos los alumnos inscritos a una asignación,
                    junto con todas sus materias y respectivas calificaciones.
                    """
        )
        public ResponseEntity<List<AlumnoMateriasDTO>> obtenerMateriasPorAlumno(
                @PathVariable String idAsignacion) {

            return ResponseEntity.ok(
            		reprobadosService.obtenerMateriasPorAlumno(idAsignacion)
            );
        }
}

