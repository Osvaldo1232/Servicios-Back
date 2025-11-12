package com.primaria.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.primaria.app.DTO.AsignacionMateriaGradoDTO;
import com.primaria.app.DTO.AsignacionMateriaGradoResumeDTO;
import com.primaria.app.DTO.DocenteMateriaDTO;
import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.Model.AsignacionMateriaGrado;
import com.primaria.app.Service.AsignacionMateriaGradoService;
import com.primaria.app.repository.DocenteGradoGrupoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/asignacion")
@CrossOrigin(origins = "*")
@Tag(name = "Asignación de Materia a Grado", description = "Endpoints para administrar las asignaciones de materias a grados escolares")
public class AsignacionMateriaGradoController {

	
	@Autowired
	private DocenteGradoGrupoRepository docenteGradoGrupoRepository;
    @Autowired
    private AsignacionMateriaGradoService asignacionMateriaGradoService;

    @Operation(
        summary = "RF4.36 Guardar una nueva asignación de materia a grado",
        description = "Crea una relación entre una materia y un grado existente en la base de datos"
    )
    @PostMapping("/guardar")
    public Map<String, String> guardar(@RequestBody AsignacionMateriaGradoDTO dto) {
        AsignacionMateriaGrado asignacion = asignacionMateriaGradoService.guardarAsignacion(dto);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Asignación creada exitosamente");
        respuesta.put("idAsignacion", asignacion.getId());

        return respuesta;
    }
    
    
    @GetMapping("/grado/{idGrado}")
    @Operation(
        summary = "RF2.3 Obtener materias asignadas a un grado",
        description = "Devuelve el ID y nombre de las materias que están asignadas a un grado específico"
    )
    
    public List<Map<String, String>> obtenerMateriasPorGrado(@PathVariable String idGrado) {
        List<Map<String, String>> materias = asignacionMateriaGradoService.obtenerMateriasPorGrado(idGrado);

        if (materias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron materias para este grado");
        }

        return materias;
    }
    
    @GetMapping("/docente/{idDocente}/ciclo/{idCiclo}/materias")
    @Operation(
        summary = "Obtener materias asignadas a un docente en un ciclo escolar",
        description = "Devuelve grado, grupo y nombre de materias del docente filtradas por ciclo escolar"
    )
    public List<DocenteMateriaDTO> obtenerMateriasPorDocenteYCiclo(
            @PathVariable String idDocente,
            @PathVariable String idCiclo) {

        List<DocenteMateriaDTO> materias = docenteGradoGrupoRepository.obtenerMateriasPorDocenteYCiclo(idDocente, idCiclo);

        if (materias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron materias para este docente en el ciclo indicado");
        }

        return materias;
    }
    @GetMapping("/listar-por-grado")
    @Operation(summary = "RFC2.3 Lista de materias y campos formativos por grado")
  
    public ResponseEntity<?> listarPorGrado(@RequestParam String idGrado) {
        try {
            List<AsignacionMateriaGradoResumeDTO> resultado = asignacionMateriaGradoService.listarAsignacionesPorGrado(idGrado);
            if (resultado.isEmpty()) {
                return ResponseEntity.ok(List.of()); // respuesta vacía válida
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
            summary = "RF3.2 Obtener materias con calificación final por grado",
            description = "Devuelve una lista de materias asignadas a un grado, mostrando la calificación actual si existe, y null si aún no ha sido registrada."
        )
        @GetMapping("/materias-por-grado")
        public ResponseEntity<List<MateriaCalificacionResDTO>> obtenerMateriasPorGrado(
                @Parameter(description = "ID del grado") @RequestParam String idGrado,
                @Parameter(description = "ID del alumno") @RequestParam String idAlumno,
                @Parameter(description = "ID del ciclo escolar") @RequestParam String idCicloEscolar
        ) {
            List<MateriaCalificacionResDTO> lista =
            		asignacionMateriaGradoService.obtenerMateriasConPromedio(idGrado, idAlumno, idCicloEscolar);
            return ResponseEntity.ok(lista);
        }
}
    
