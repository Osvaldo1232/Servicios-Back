package com.primaria.app.controller;



import com.primaria.app.DTO.GrupoDTO;

import com.primaria.app.Model.Grupo;

import com.primaria.app.Service.GrupoService;

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
@RequestMapping("/grupos")
@Tag(name = "Grupos", description = "Operaciones relacionadas con los grupos")
public class GruposController {

	 @Autowired
	    private GrupoService grupoService;

	    @Operation(summary = "Listar todos los grupos")
	    @GetMapping
	    public ResponseEntity<List<GrupoDTO>> listarTodos() {
	        return ResponseEntity.ok(grupoService.listarTodos());
	    }

	    @Operation(summary = "Obtener un grupo por UUID")
	    @GetMapping("Obtener/{uuid}")
	    public ResponseEntity<GrupoDTO> obtenerPorUuid(@PathVariable String uuid) {
	        Optional<GrupoDTO> grupo = grupoService.obtenerPorUuid(uuid);
	        return grupo.map(ResponseEntity::ok)
	                    .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping("/NuevoGrupo")
	    @Operation(summary = "Registrar Grupo")
	    public ResponseEntity<?> registrarGrupo(@RequestBody GrupoDTO dto) {
	        Grupo grupo = new Grupo();
	        grupo.setNombre(dto.getNombre());
	       grupo.setEstatus(dto.getEstatus());
	        grupoService.save(grupo);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Grupo registrado exitosamente");
	        response.put("id", grupo.getId());

	        return ResponseEntity.ok(response);
	        
	        
	    }

	    @Operation(summary = "Actualizar un grupo existente")
	    @PutMapping("Actualizar/{uuid}")
	    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody GrupoDTO grupoDTO) {
	        boolean actualizado = grupoService.actualizar(uuid, grupoDTO);
	        if (actualizado) {
	            return ResponseEntity.ok("Grupo actualizado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @Operation(summary = "Eliminar un grupo por UUID")
	    @DeleteMapping("Eliminar/{uuid}")
	    public ResponseEntity<String> eliminar(@PathVariable String uuid) {
	        boolean eliminado = grupoService.eliminar(uuid);
	        if (eliminado) {
	            return ResponseEntity.ok("Grupo eliminado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
