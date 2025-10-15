package com.primaria.app.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.primaria.app.DTO.EstatusDTO;
import com.primaria.app.DTO.ProfesorDTO;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Profesor;
import com.primaria.app.Model.Usuario;
import com.primaria.app.Service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Profesores")
@Tag(name = "Profesores", description = "Operaciones para Profesores")
public class ProfesorController {
	
	  private final UsuarioService usuarioService;
	  
	  public ProfesorController(UsuarioService usuarioService) {
	        this.usuarioService = usuarioService;
	    }
	
	  @PutMapping("/profesor/{id}")
	    @Operation(summary = "Actualizar Profesor")
	    public ResponseEntity<?> actualizarProfesor(@PathVariable String id, @RequestBody ProfesorDTO dto) {
	        Optional<Usuario> optUsuario = usuarioService.findById(id);
	        if (optUsuario.isEmpty() || !(optUsuario.get() instanceof Profesor)) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado");
	        }
	        Profesor profesor = (Profesor) optUsuario.get();

	        profesor.setNombre(dto.getNombre());
	        profesor.setApellidos(dto.getApellidos());
	        profesor.setEmail(dto.getEmail());
	        profesor.setFechaNacimiento(dto.getFechaNacimiento());
	        profesor.setSexo(dto.getSexo());
	        profesor.setEspecialidad(dto.getEspecialidad());
	        profesor.setEstatus(dto.getEstatus());
	        profesor.setTelefono(dto.getTelefono());
	        profesor.setRfc(dto.getRfc());
	        profesor.setClavePresupuestal(dto.getClavePresupuestal());
	        usuarioService.update(profesor, dto.getPassword());

	        return ResponseEntity.ok("Profesor actualizado exitosamente");
	    }
	    

	  @PatchMapping("/profesor/{id}/estatus")
	  @Operation(summary = "Actualizar Estatus de Profesor", description = "Actualiza únicamente el estatus de un profesor existente por su ID")
	  public ResponseEntity<?> actualizarEstatusProfesor(
	          @PathVariable String id,
	          @Valid @RequestBody EstatusDTO dto) {

	      Optional<Usuario> optUsuario = usuarioService.findById(id);

	      if (optUsuario.isEmpty() || !(optUsuario.get() instanceof Profesor)) {
	          return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                  .body(Map.of("error", "Profesor no encontrado"));
	      }

	      Profesor profesor = (Profesor) optUsuario.get();
	      profesor.setEstatus(dto.getEstatus());
	      usuarioService.update(profesor, null);

	      return ResponseEntity.ok(Map.of("message", "Estatus actualizado exitosamente"));
	  }

}
