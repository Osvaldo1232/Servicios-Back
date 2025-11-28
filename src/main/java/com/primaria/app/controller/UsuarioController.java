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
    @Operation(summary = "RF4.5 Registrar Estudiante")
    public ResponseEntity<?> registrarEstudiante(@RequestBody EstudianteDTO dto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellidoMaterno(dto.getApellidoMaterno());
        estudiante.setApellidoPaterno(dto.getApellidoPaterno());
        estudiante.setEstatus(dto.getEstatus());
        estudiante.setEmail(dto.getEmail());
        estudiante.setPassword(dto.getPassword());
        estudiante.setMatricula(dto.getMatricula());
        estudiante.setCurp(dto.getCurp());
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
    @Operation(summary = "RF4.1 Registrar Profesor")
    public ResponseEntity<?> registrarProfesor(@RequestBody ProfesorDTO dto) {
        Profesor profesor = new Profesor();
        profesor.setNombre(dto.getNombre());
        profesor.setApellidoMaterno(dto.getApellidoMaterno());
       profesor.setApellidoPaterno(dto.getApellidoPaterno());
       profesor.setEstatus(dto.getEstatus());
        profesor.setEmail(dto.getEmail());
        profesor.setPassword(dto.getPassword());
        profesor.setRol(Rol.PROFESOR);
        
        //  Nuevos campos
        profesor.setFechaNacimiento(dto.getFechaNacimiento());
        profesor.setSexo(dto.getSexo());
        profesor.setEspecialidad(dto.getEspecialidad());
       
        profesor.setTelefono(dto.getTelefono());
        profesor.setRfc(dto.getRfc());
        profesor.setClavePresupuestal(dto.getClavePresupuestal());
        usuarioService.save(profesor);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Profesor registrado exitosamente");
        response.put("id", profesor.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/director")
    @Operation(summary = "Registrar Director")
    public ResponseEntity<?> registrarDirector(@RequestBody DirectorDTO dto) {
        Director director = new Director();
        director.setNombre(dto.getNombre());
        director.setApellidoMaterno(dto.getApellidoMaterno());
        director.setApellidoPaterno(dto.getApellidoPaterno());
        director.setEstatus(dto.getEstatus());
        
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
    
    @Operation(summary = "RF2.1 Obtener usuario por ID con detalles según el rol")
    @GetMapping("/BuscarUsuario/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable String id) {
        Object usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }
    
    @Operation(summary = "RF4.3  RF4.7 Actualizar estatus de un usuario")
   
    @PatchMapping("/{id}/estatus")
    public ResponseEntity<String> actualizarEstatus(
            @PathVariable("id") String id,
            @RequestParam("estatus") Estatus estatus) {

        boolean actualizado = usuarioService.actualizarEstatus(id, estatus);
        if (actualizado) {
            return ResponseEntity.ok("Estatus actualizado correctamente a " + estatus);
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }
    
    
}
