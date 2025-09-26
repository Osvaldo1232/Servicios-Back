package com.primaria.app.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.primaria.app.DTO.TrimestresDTO;
import com.primaria.app.DTO.TrimestresResumenDTO;
import com.primaria.app.Model.Trimestres;
import com.primaria.app.Service.TrimestresService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Trimestres")
@Tag(name = "Trimestres", description = "Operaciones relacionadas con los grupos")
public class TrimestreController {

	 @Autowired
	    private TrimestresService trimestresService;

	    @Operation(summary = "Listar todos las materias")
	    @GetMapping
	    public ResponseEntity<Page<TrimestresDTO>> listarMaterias(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(defaultValue = "id") String sortBy
	    ) {
	        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	        Page<TrimestresDTO> materias = trimestresService.listarTodos(pageable);
	        return ResponseEntity.ok(materias);
	    }
	    
	    @Operation(summary = "Obtener un Campo Formativo por UUID")
	    @GetMapping("Obtener/{uuid}")
	    public ResponseEntity<TrimestresDTO> obtenerPorUuid(@PathVariable String uuid) {
	        Optional<TrimestresDTO> grupo = trimestresService.obtenerPorUuid(uuid);
	        return grupo.map(ResponseEntity::ok)
	                    .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping("/NuevoCampo")
	    @Operation(summary = "Registrar Campo Formativo")
	    public ResponseEntity<?> registrarGrupo(@RequestBody TrimestresDTO dto) {
	    	Trimestres grupo = new Trimestres();
	        grupo.setNombre(dto.getNombre());
	       grupo.setEstatus(dto.getEstatus());
	       
	       trimestresService.save(grupo);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Trimestre registrado exitosamente");
	        response.put("id", grupo.getId());

	        return ResponseEntity.ok(response);
	        
	        
	    }

	    @Operation(summary = "Actualizar un Campo Formativo existente")
	    @PutMapping("Actualizar/{uuid}")
	    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody TrimestresDTO campoFormativoDTO) {
	        boolean actualizado = trimestresService.actualizar(uuid, campoFormativoDTO);
	        if (actualizado) {
	            return ResponseEntity.ok("Campo Formativo actualizado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @Operation(summary = "Eliminar un Campo Formativo por UUID")
	    @DeleteMapping("Eliminar/{uuid}")
	    public ResponseEntity<String> eliminar(@PathVariable String uuid) {
	        boolean eliminado = trimestresService.eliminar(uuid);
	        if (eliminado) {
	            return ResponseEntity.ok("Grupo eliminado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @GetMapping("/combo")
	    public List<TrimestresResumenDTO> obtenerCamposActivos() {
	        return trimestresService.obtenerActivos();
	    }
}
