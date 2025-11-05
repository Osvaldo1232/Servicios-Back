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

	    @Operation(summary = "RF4.22:  Listar todos los Ciclo Escolar")
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
	    @Operation(summary = "RF4.20 Registrar Ciclo Escolar")
	    public ResponseEntity<?> registrarCiclo(@RequestBody CicloEscolarDTO dto) {
	        Map<String, Object> response = new HashMap<>();
	        try {
	            CicloEscolarDTO guardado = cicloEscolarService.guardar(dto);
	            response.put("message", "Ciclo Escolar registrado exitosamente");
	            response.put("id", guardado.getId());
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            response.put("error", e.getMessage());
	            return ResponseEntity.badRequest().body(response);
	        } catch (Exception e) {
	        	 e.printStackTrace();
	            response.put("error", "Ocurrió un error al registrar el ciclo escolar.");
	            return ResponseEntity.internalServerError().body(response);
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
