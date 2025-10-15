package com.primaria.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.primaria.app.DTO.AsignacionMateriaGradoDTO;
import com.primaria.app.DTO.DocenteMateriaDTO;
import com.primaria.app.Model.AsignacionMateriaGrado;
import com.primaria.app.Service.AsignacionMateriaGradoService;
import com.primaria.app.repository.DocenteGradoGrupoRepository;

import io.swagger.v3.oas.annotations.Operation;
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
        summary = "Guardar una nueva asignación de materia a grado",
        description = "Crea una relación entre una materia y un grado existente en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o materia/grado no encontrados"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
        summary = "Obtener materias asignadas a un grado",
        description = "Devuelve el ID y nombre de las materias que están asignadas a un grado específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de materias obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Grado no encontrado o sin asignaciones"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de materias obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontraron asignaciones para este docente y ciclo"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<DocenteMateriaDTO> obtenerMateriasPorDocenteYCiclo(
            @PathVariable String idDocente,
            @PathVariable String idCiclo) {

        List<DocenteMateriaDTO> materias = docenteGradoGrupoRepository.obtenerMateriasPorDocenteYCiclo(idDocente, idCiclo);

        if (materias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron materias para este docente en el ciclo indicado");
        }

        return materias;
    }
   
}
    
