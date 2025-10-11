package com.primaria.app.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.primaria.app.DTO.CampoFormativoDTO;
import com.primaria.app.DTO.CampoFormativoResumenDTO;

import com.primaria.app.Model.CampoFormativo;

import com.primaria.app.Service.CampoFormativoService;


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
@RequestMapping("/campoFormativo")
@Tag(name = "CampoFormativo", description = "Operaciones relacionadas con los grupos")
public class CampoFormativoController {

	 @Autowired
	    private CampoFormativoService campoFormativoService;

	    @Operation(summary = "Listar todos las materias")
	    @GetMapping
	    public ResponseEntity<Page<CampoFormativoDTO>> listarMaterias(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(defaultValue = "id") String sortBy
	    ) {
	        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	        Page<CampoFormativoDTO> materias = campoFormativoService.listarTodos(pageable);
	        return ResponseEntity.ok(materias);
	    }
	    
	    @Operation(summary = "Obtener un Campo Formativo por UUID")
	    @GetMapping("Obtener/{uuid}")
	    public ResponseEntity<CampoFormativoDTO> obtenerPorUuid(@PathVariable String uuid) {
	        Optional<CampoFormativoDTO> grupo = campoFormativoService.obtenerPorUuid(uuid);
	        return grupo.map(ResponseEntity::ok)
	                    .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping("/NuevoCampo")
	    @Operation(summary = "Registrar Campo Formativo")
	    public ResponseEntity<?> registrarGrupo(@RequestBody CampoFormativoDTO dto) {
	    	CampoFormativo grupo = new CampoFormativo();
	        grupo.setNombre(dto.getNombre());
	       grupo.setEstatus(dto.getEstatus());
	       
	       campoFormativoService.save(grupo);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Grupo registrado exitosamente");
	        response.put("id", grupo.getId());

	        return ResponseEntity.ok(response);
	        
	        
	    }

	    @Operation(summary = "Actualizar un Campo Formativo existente")
	    @PutMapping("Actualizar/{uuid}")
	    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody CampoFormativoDTO campoFormativoDTO) {
	        boolean actualizado = campoFormativoService.actualizar(uuid, campoFormativoDTO);
	        if (actualizado) {
	            return ResponseEntity.ok("Campo Formativo actualizado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @Operation(summary = "Eliminar un Campo Formativo por UUID")
	    @DeleteMapping("Eliminar/{uuid}")
	    public ResponseEntity<String> eliminar(@PathVariable String uuid) {
	        boolean eliminado = campoFormativoService.eliminar(uuid);
	        if (eliminado) {
	            return ResponseEntity.ok("Grupo eliminado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @GetMapping("/combo")
	    public List<CampoFormativoResumenDTO> obtenerCamposActivos() {
	        return campoFormativoService.obtenerActivos();
	    }
}
