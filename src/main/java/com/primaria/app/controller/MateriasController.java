package com.primaria.app.controller;

import java.util.List;

import com.primaria.app.DTO.MateriaCampoDTO;
import com.primaria.app.DTO.MateriaDTO;
import com.primaria.app.Model.CampoFormativo;
import com.primaria.app.Model.Materia;

import com.primaria.app.Service.MateriasService;
import com.primaria.app.repository.CampoFormativoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/materias")
@Tag(name = "Materias", description = "Operaciones relacionadas con las materias")
public class MateriasController {

	 @Autowired
	    private MateriasService materiasService;

	 
	 
	 @Operation(summary = "RF4.19 Listar todas las materias con su campo formativo")
	    @GetMapping("/listar")
	    public ResponseEntity<List<MateriaCampoDTO>> listarMateriasCa() {
	        List<MateriaCampoDTO> materias = materiasService.listarMateriasConCampoFormativo();
	        return ResponseEntity.ok(materias);
	    }
	 
	 @Operation(summary = "RF4.19 Listar todas las materias")
	 @GetMapping("mostrarmaterias")
	 public ResponseEntity<List<MateriaDTO>> listarMaterias() {
	     List<MateriaDTO> materias = materiasService.listarTodos();
	     return ResponseEntity.ok(materias);
	 }
	    @Autowired
	    private CampoFormativoRepository campoFormativoRepository;
	    @PostMapping("/nueva")
	    @Operation(summary = "RF4.17  Registrar nueva materia")
	    public ResponseEntity<?> registrarMateria(@RequestBody MateriaDTO dto) {
	        CampoFormativo campo = campoFormativoRepository.findById(dto.getCampoFormativoId())
	            .orElseThrow(() -> new RuntimeException("Campo formativo no encontrado"));

	        Materia materia = new Materia();
	        materia.setNombre(dto.getNombre());
	        materia.setEstatus(dto.getEstatus());
	        materia.setCampoFormativo(campo);
	        Materia guardada = materiasService.save(materia);
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Materia registrada exitosamente");
	        response.put("id", guardada.getId());

	        return ResponseEntity.ok(response);
	    }
	    @Operation(summary = "RF4.18  Actualizar un materia existente")
	    @PutMapping("Actualizar/{uuid}")
	    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody MateriaDTO grupoDTO) {
	        boolean actualizado = materiasService.actualizar(uuid, grupoDTO);
	        if (actualizado) {
	            return ResponseEntity.ok("Materia actualizada exitosamente.");
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
