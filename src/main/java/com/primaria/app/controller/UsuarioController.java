package com.primaria.app.controller;

import com.primaria.app.DTO.DirectorDTO;
import com.primaria.app.DTO.EstudianteDTO;
import com.primaria.app.DTO.ProfesorDTO;
import com.primaria.app.Model.*;
import com.primaria.app.Service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones para registrar usuarios de distintos roles")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/estudiante")
    @Operation(summary = "Registrar Estudiante")
    public ResponseEntity<?> registrarEstudiante(@RequestBody EstudianteDTO dto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellidos(dto.getApellidos());
        estudiante.setEmail(dto.getEmail());
        estudiante.setPassword(dto.getPassword());
        estudiante.setMatricula(dto.getMatricula());
        estudiante.setCurp(dto.getCurp());
        estudiante.setEstatus(dto.getEstatus());
        estudiante.setRol(Rol.ESTUDIANTE);
        //  Nuevos campos
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());
        estudiante.setSexo(dto.getSexo());

        usuarioService.save(estudiante);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Estudiante registrado exitosamente");
        response.put("id", estudiante.getId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/profesor")
    @Operation(summary = "Registrar Profesor")
    public ResponseEntity<?> registrarProfesor(@RequestBody ProfesorDTO dto) {
        Profesor profesor = new Profesor();
        profesor.setNombre(dto.getNombre());
        profesor.setApellidos(dto.getApellidos());
        profesor.setEmail(dto.getEmail());
        profesor.setPassword(dto.getPassword());
        profesor.setRol(Rol.PROFESOR);

        //  Nuevos campos
        profesor.setFechaNacimiento(dto.getFechaNacimiento());
        profesor.setSexo(dto.getSexo());
        profesor.setEspecialidad(dto.getEspecialidad());
        profesor.setEstatus(dto.getEstatus());
        profesor.setTelefono(dto.getTelefono());
        profesor.setRfc(dto.getRfc());
        profesor.setClavePresupuestal(dto.getClavePresupuestal());
        usuarioService.save(profesor);
        return ResponseEntity.ok("Profesor registrado exitosamente");
    }

    @PostMapping("/director")
    @Operation(summary = "Registrar Director")
    public ResponseEntity<?> registrarDirector(@RequestBody DirectorDTO dto) {
        Director director = new Director();
        director.setNombre(dto.getNombre());
        director.setApellidos(dto.getApellidos());
        director.setEmail(dto.getEmail());
        director.setPassword(dto.getPassword());
        director.setTelefono(dto.getTelefono());
        
        director.setDepartamento(dto.getDepartamento());
        director.setRol(Rol.DIRECTOR);

        //  Nuevos campos
        director.setFechaNacimiento(dto.getFechaNacimiento());
        director.setSexo(dto.getSexo());

        usuarioService.save(director);
        return ResponseEntity.ok("Director registrado exitosamente");
    }
    
    @Operation(summary = "Obtener usuario por ID con detalles según el rol")
    @GetMapping("/BuscarUsuario/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable String id) {
        Object usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }
    
    @PutMapping("/profesor/{id}")
    @Operation(summary = "Actualizar Profesor")
    public ResponseEntity<?> actualizarProfesor(@PathVariable String id, @RequestBody ProfesorDTO dto) {
        Optional<Usuario> optUsuario = usuarioService.findById(id);

        if (optUsuario.isEmpty() || !(optUsuario.get() instanceof Profesor)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado");
        }

        Profesor profesor = (Profesor) optUsuario.get();

        // Actualiza los campos
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

        // Si viene una nueva contraseña, la re-encripta
        usuarioService.update(profesor, dto.getPassword());

        return ResponseEntity.ok("Profesor actualizado exitosamente");
    }
    

    @PatchMapping(path = "/profesor/{id}/estatus", consumes = "application/json", produces = "application/json")
    @Operation(
        summary = "Actualizar Estatus de Profesor",
        description = "Actualiza únicamente el estatus de un profesor existente por su ID",
        tags = { "Profesores" }
    )
    public ResponseEntity<?> actualizarEstatusProfesor(@PathVariable String id, @RequestBody Map<String, String> request) {
        Optional<Usuario> optUsuario = usuarioService.findById(id);

        if (optUsuario.isEmpty() || !(optUsuario.get() instanceof Profesor)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Profesor no encontrado"));
        }

        Profesor profesor = (Profesor) optUsuario.get();

        // Obtener estatus del JSON
        String nuevoEstatus = request.get("estatus");
        if (nuevoEstatus == null || nuevoEstatus.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Debe enviar un estatus válido"));
        }

        profesor.setEstatus(Estatus.valueOf(nuevoEstatus.toUpperCase()));

        usuarioService.update(profesor, null); // null porque no estamos actualizando la contraseña

        return ResponseEntity.ok(Map.of("message", "Estatus actualizado exitosamente"));
    }


}
