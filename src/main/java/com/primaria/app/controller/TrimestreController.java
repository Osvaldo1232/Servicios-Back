package com.primaria.app.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.primaria.app.DTO.TrimestresDTO;
import com.primaria.app.DTO.TrimestresResumenDTO;
import com.primaria.app.Model.Trimestres;
import com.primaria.app.Service.TrimestresService;

import org.springframework.http.HttpHeaders;
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

	 @Operation(summary = "Listar todos los trimestres")
	 @GetMapping
	 public ResponseEntity<List<TrimestresDTO>> listarTrimestres() {
	     List<TrimestresDTO> trimestres = trimestresService.listarTodos();
	     return ResponseEntity.ok(trimestres);
	 }

	    
	    @Operation(summary = "Obtener un Campo Formativo por UUID")
	    @GetMapping("Obtener/{uuid}")
	    public ResponseEntity<TrimestresDTO> obtenerPorUuid(@PathVariable String uuid) {
	        Optional<TrimestresDTO> grupo = trimestresService.obtenerPorUuid(uuid);
	        return grupo.map(ResponseEntity::ok)
	                    .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping("/NuevoCampo")
	    @Operation(summary = "RF4.23 Registrar Campo Formativo")
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

	    @Operation(summary = "RF4.24 Actualizar un Campo Formativo existente")
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
	    
	    @GetMapping(value = "/imprimir", produces = MediaType.APPLICATION_PDF_VALUE)
	    @Operation(summary = "Genera un PDF con la lista de todos los trimestres")
	    public ResponseEntity<byte[]> generarPDF() {
	        try {
	            byte[] pdfBytes = trimestresService.generarPDF();

	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_PDF);
	            headers.setContentDispositionFormData("filename", "trimestres.pdf");

	            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

	        } catch (Exception e) {
	            return ResponseEntity
	                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(("Error al generar PDF: " + e.getMessage()).getBytes());
	        }
	    }

}
