package com.primaria.app.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.primaria.app.DTO.Tipos_EvaluacionDTO;
import com.primaria.app.DTO.Tipos_EvaluacionResumenDTO;
import com.primaria.app.DTO.TutorDTO;
import com.primaria.app.DTO.TutorResumenDTO;
import com.primaria.app.Model.Tipos_Evaluacion;
import com.primaria.app.Model.Tutor;
import com.primaria.app.Service.Tipos_EvaluacionService;
import com.primaria.app.Service.TutorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Tipos_Evaluacion")
@Tag(name = "Tipos_Evaluacion", description = "Operaciones relacionadas con los Tipos de Evaluación")
public class TiposEvaluacionController {

	 @Autowired
	    private Tipos_EvaluacionService tipos_EvaluacionService;

	    @Operation(summary = "Listar todos los Tipos de Evaluación")
	    @GetMapping
	    public ResponseEntity<Page<Tipos_EvaluacionDTO>> listarTipos_Evaluacion(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(defaultValue = "id") String sortBy
	    ) {
	        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	        Page<Tipos_EvaluacionDTO> tipos = tipos_EvaluacionService.listarTodos(pageable);
	        return ResponseEntity.ok(tipos);
	    }
	    
	    @Operation(summary = "Obtener un tipo de evaluación por UUID")
	    @GetMapping("Obtener/{uuid}")
	    public ResponseEntity<Tipos_EvaluacionDTO> obtenerPorUuid(@PathVariable String uuid) {
	        Optional<Tipos_EvaluacionDTO> tipos = tipos_EvaluacionService.obtenerPorUuid(uuid);
	        return tipos.map(ResponseEntity::ok)
	                    .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping("/NuevoTipoDeEvaluacion")
	    @Operation(summary = "Registrar Tipo de Evaluacion")
	    public ResponseEntity<?> registrarTipoEvaluacion(@RequestBody Tipos_EvaluacionDTO dto) {
	    	Tipos_Evaluacion tipos = new Tipos_Evaluacion();
	    	tipos.setNombre(dto.getNombre());
	    	tipos.setEstatus(dto.getEstatus());
           
	       
	       
	    	tipos_EvaluacionService.save(tipos);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Tipo de Evaluacion registrado exitosamente");
	        response.put("id", tipos.getId());

	        return ResponseEntity.ok(response);
	        
	        
	    }

	    @Operation(summary = "Actualizar un Tipo de Evaluacion existente")
	    @PutMapping("Actualizar/{uuid}")
	    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody Tipos_EvaluacionDTO tipos_EvaluacionDTO) {
	        boolean actualizado = tipos_EvaluacionService.actualizar(uuid, tipos_EvaluacionDTO);
	        if (actualizado) {
	            return ResponseEntity.ok("Tutor actualizado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @Operation(summary = "Eliminar un Tipo de Evaluacion por UUID")
	    @DeleteMapping("Eliminar/{uuid}")
	    public ResponseEntity<String> eliminar(@PathVariable String uuid) {
	        boolean eliminado = tipos_EvaluacionService.eliminar(uuid);
	        if (eliminado) {
	            return ResponseEntity.ok("Tipo de evaluacion eliminado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @GetMapping("/combo")
	    public List<Tipos_EvaluacionResumenDTO> obtenerCamposActivos() {
	        return tipos_EvaluacionService.obtenerActivos();
	    }
}
