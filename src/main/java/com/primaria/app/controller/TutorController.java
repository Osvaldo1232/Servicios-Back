package com.primaria.app.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.primaria.app.DTO.TutorDTO;
import com.primaria.app.DTO.TutorResumenDTO;
import com.primaria.app.Model.Tutor;
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
@RequestMapping("/Tutor")
@Tag(name = "CampoFormativo", description = "Operaciones relacionadas con los grupos")
public class TutorController {

	 @Autowired
	    private TutorService tutorService;

	    @Operation(summary = "Listar todos los tutores")
	    @GetMapping
	    public ResponseEntity<Page<TutorDTO>> listarTutores(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size,
	            @RequestParam(defaultValue = "id") String sortBy
	    ) {
	        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	        Page<TutorDTO> tutores = tutorService.listarTodos(pageable);
	        return ResponseEntity.ok(tutores);
	    }
	    
	    @Operation(summary = "Obtener un tutor por UUID")
	    @GetMapping("Obtener/{uuid}")
	    public ResponseEntity<TutorDTO> obtenerPorUuid(@PathVariable String uuid) {
	        Optional<TutorDTO> tutor = tutorService.obtenerPorUuid(uuid);
	        return tutor.map(ResponseEntity::ok)
	                    .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping("/NuevoTutor")
	    @Operation(summary = "Registrar Campo Formativo")
	    public ResponseEntity<?> registrarGrupo(@RequestBody TutorDTO dto) {
	    	Tutor tutor = new Tutor();
	    	tutor.setNombre(dto.getNombre());
	    	tutor.setEstatus(dto.getEstatus());
	    	tutor.setApellidos(dto.getApellidos());
	    	tutor.setCorreo(dto.getCorreo());
	    	tutor.setParentesco(dto.getParentesco());
	    	tutor.setTelefono(dto.getTelefono());
           
	       
	       
	       tutorService.save(tutor);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Tutot registrado exitosamente");
	        response.put("id", tutor.getId());

	        return ResponseEntity.ok(response);
	        
	        
	    }

	    @Operation(summary = "Actualizar un Tutor existente")
	    @PutMapping("Actualizar/{uuid}")
	    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody TutorDTO tutorDTO) {
	        boolean actualizado = tutorService.actualizar(uuid, tutorDTO);
	        if (actualizado) {
	            return ResponseEntity.ok("Tutor actualizado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @Operation(summary = "Eliminar un Tutor por UUID")
	    @DeleteMapping("Eliminar/{uuid}")
	    public ResponseEntity<String> eliminar(@PathVariable String uuid) {
	        boolean eliminado = tutorService.eliminar(uuid);
	        if (eliminado) {
	            return ResponseEntity.ok("Tutor eliminado exitosamente.");
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    @GetMapping("/combo")
	    public List<TutorResumenDTO> obtenerCamposActivos() {
	        return tutorService.obtenerActivos();
	    }
}
