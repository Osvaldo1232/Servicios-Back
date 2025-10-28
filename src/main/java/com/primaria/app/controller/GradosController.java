package com.primaria.app.controller;
import com.primaria.app.DTO.GradoDTO;
import com.primaria.app.Model.Grado;
import com.primaria.app.Service.GradosService;
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
@RequestMapping("/grados")
@Tag(name = "Grados", description = "Operaciones relacionadas con los grados")
public class GradosController {

	 @Autowired
	    private GradosService gradoService;

	    @Operation(summary = "Listar todos los grapos")
	    @GetMapping
	    public ResponseEntity<List<GradoDTO>> listarTodos() {
	        return ResponseEntity.ok(gradoService.listarTodos());
	    }

	    @Operation(summary = "Obtener un grupo por UUID")
	    @GetMapping("Obtener/{uuid}")
	    public ResponseEntity<GradoDTO> obtenerPorUuid(@PathVariable String uuid) {
	        Optional<GradoDTO> grupo = gradoService.obtenerPorUuid(uuid);
	        return grupo.map(ResponseEntity::ok)
	                    .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping("/NuevoGrado")
	    @Operation(summary = "RF4.19  Registrar Grado")
	    public ResponseEntity<?> registrarGrupo(@RequestBody GradoDTO dto) {
	        Grado grupo = new Grado();
	        grupo.setNombre(dto.getNombre());
	        grupo.setEstatus(dto.getEstatus());
	        gradoService.save(grupo);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Grado registrado exitosamente");
	        response.put("id", grupo.getId());

	        return ResponseEntity.ok(response);
	        
	        
	    }

	    @Operation(summary = "RF4.20 Actualizar un grado existente")
	    @PutMapping("Actualizar/{uuid}")
	    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody GradoDTO gradoDTO) {
	        boolean actualizado = gradoService.actualizar(uuid, gradoDTO);
	        if (actualizado) {
	            return ResponseEntity.ok("Grado actualizado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @Operation(summary = "Eliminar un grado por UUID")
	    @DeleteMapping("Eliminar/{uuid}")
	    public ResponseEntity<String> eliminar(@PathVariable String uuid) {
	        boolean eliminado = gradoService.eliminar(uuid);
	        if (eliminado) {
	            return ResponseEntity.ok("Grado eliminado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
