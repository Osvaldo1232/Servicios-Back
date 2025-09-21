package com.primaria.app.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.primaria.app.DTO.MateriaDTO;
import com.primaria.app.Model.Materia;

import com.primaria.app.Service.MateriasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/materias")
@Tag(name = "Materias", description = "Operaciones relacionadas con los grupos")
public class MateriasController {

	 @Autowired
	    private MateriasService materiasService;

	    @Operation(summary = "Listar todos las materias")
	    @GetMapping
	    public ResponseEntity<Page<MateriaDTO>> listarMaterias(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(defaultValue = "id") String sortBy
	    ) {
	        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	        Page<MateriaDTO> materias = materiasService.listarTodos(pageable);
	        return ResponseEntity.ok(materias);
	    }
	    
	    @Operation(summary = "Obtener un Materia por UUID")
	    @GetMapping("Obtener/{uuid}")
	    public ResponseEntity<MateriaDTO> obtenerPorUuid(@PathVariable String uuid) {
	        Optional<MateriaDTO> grupo = materiasService.obtenerPorUuid(uuid);
	        return grupo.map(ResponseEntity::ok)
	                    .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping("/NuevoGrupo")
	    @Operation(summary = "Registrar Materia")
	    public ResponseEntity<?> registrarGrupo(@RequestBody MateriaDTO dto) {
	        Materia grupo = new Materia();
	        grupo.setNombre(dto.getNombre());
	       grupo.setEstatus(dto.getEstatus());
	        materiasService.save(grupo);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Grupo registrado exitosamente");
	        response.put("id", grupo.getId());

	        return ResponseEntity.ok(response);
	        
	        
	    }

	    @Operation(summary = "Actualizar un materia existente")
	    @PutMapping("Actualizar/{uuid}")
	    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody MateriaDTO grupoDTO) {
	        boolean actualizado = materiasService.actualizar(uuid, grupoDTO);
	        if (actualizado) {
	            return ResponseEntity.ok("Grupo actualizado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @Operation(summary = "Eliminar un materia por UUID")
	    @DeleteMapping("Eliminar/{uuid}")
	    public ResponseEntity<String> eliminar(@PathVariable String uuid) {
	        boolean eliminado = materiasService.eliminar(uuid);
	        if (eliminado) {
	            return ResponseEntity.ok("Grupo eliminado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
