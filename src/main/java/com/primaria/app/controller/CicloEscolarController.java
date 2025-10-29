package com.primaria.app.controller;
import com.primaria.app.DTO.CicloEscolarDTO;
import com.primaria.app.Model.CicloEscolar;
import com.primaria.app.Service.CicloEscolarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ciclosescolares")
@Tag(name = "Ciclo Escolar", description = "Operaciones para gestionar ciclos escolares")
public class CicloEscolarController {
	 @Autowired
	    private CicloEscolarService cicloEscolarService;

	    @Operation(summary = "Listar todos los Ciclo Escolar")
	    @GetMapping
	    public ResponseEntity<List<CicloEscolarDTO>> listarTodos() {
	        return ResponseEntity.ok(cicloEscolarService.listarTodos());
	    }

	    @Operation(summary = "Obtener un Ciclo Escolar por UUID")
	    @GetMapping("Obtener/{uuid}")
	    public ResponseEntity<CicloEscolarDTO> obtenerPorUuid(@PathVariable String uuid) {
	        Optional<CicloEscolarDTO> grupo = cicloEscolarService.obtenerPorUuid(uuid);
	        return grupo.map(ResponseEntity::ok)
	                    .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping("/NuevoCiclo")
	    @Operation(summary = "RF4.17  Registrar Ciclo Escolar")
	    public ResponseEntity<?> registrarGrupo(@RequestBody CicloEscolarDTO dto) {
	        CicloEscolar ciclo = new CicloEscolar();
	        ciclo.setFechaInicio(dto.getFechaInicio());
	        ciclo.setFechaFin(dto.getFechaFin());
	        ciclo.setEstatus(dto.getEstatus());
	       cicloEscolarService.save(ciclo);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Ciclo Escolar registrado exitosamente");
	        response.put("id", ciclo.getId());
	        return ResponseEntity.ok(response);	        	        
	    }

	    @Operation(summary = "RF4.18  Actualizar un ciclo existente")
	    @PutMapping("Actualizar/{uuid}")
	    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody CicloEscolarDTO cicloEscolarDTO) {
	        boolean actualizado = cicloEscolarService.actualizar(uuid, cicloEscolarDTO);
	        if (actualizado) {
	            return ResponseEntity.ok("Ciclo Escolar actualizado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @Operation(summary = "Eliminar un grupo por UUID")
	    @DeleteMapping("Eliminar/{uuid}")
	    public ResponseEntity<String> eliminar(@PathVariable String uuid) {
	        boolean eliminado = cicloEscolarService.eliminar(uuid);
	        if (eliminado) {
	            return ResponseEntity.ok("Ciclo Escolar eliminado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    
	    @Operation(summary = "Obtener el ciclo escolar más reciente")
	    @GetMapping("/reciente")
	    public ResponseEntity<?> obtenerCicloMasReciente() {
	        return cicloEscolarService.obtenerCicloMasReciente()
	                .<ResponseEntity<?>>map(ResponseEntity::ok)
	                .orElse(ResponseEntity.status(404)
	                        .body(Map.of("mensaje", "No se encontró ningún ciclo escolar registrado")));
	    }

}
