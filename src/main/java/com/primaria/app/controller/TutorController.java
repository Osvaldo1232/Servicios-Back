package com.primaria.app.controller;

import com.primaria.app.DTO.AlumnoTutorDTO;
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
@Tag(name = "Tutores", description = "Operaciones relacionadas con los tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    
    
    @Operation(summary = "Listar todos los tutores")
    @GetMapping
    public ResponseEntity<List<TutorDTO>> listarTutores() {
        List<TutorDTO> tutores = tutorService.listarTodos();
        return ResponseEntity.ok(tutores);
    }

    @Operation(summary = "Obtener un tutor por UUID")
    @GetMapping("/Obtener/{uuid}")
    public ResponseEntity<TutorDTO> obtenerPorUuid(@PathVariable String uuid) {
        Optional<TutorDTO> tutor = tutorService.obtenerPorUuid(uuid);
        return tutor.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/NuevoTutor")
    @Operation(summary = "RF4.10 Registrar un nuevo tutor")
    public ResponseEntity<?> registrarTutor(@RequestBody TutorDTO dto) {
        Tutor tutor = new Tutor();
        tutor.setNombre(dto.getNombre());
        tutor.setApellidoPaterno(dto.getApellidoPaterno());
        tutor.setApellidoMaterno(dto.getApellidoMaterno());
        tutor.setCorreo(dto.getCorreo());
        tutor.setTelefono(dto.getTelefono());
        tutor.setEstatus(dto.getEstatus());

        tutorService.save(tutor);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Tutor registrado exitosamente");
        response.put("id", tutor.getId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "RF4.11 Actualizar un Tutor existente")
    @PutMapping("/Actualizar/{uuid}")
    public ResponseEntity<String> actualizar(@PathVariable String uuid, @RequestBody TutorDTO tutorDTO) {
        boolean actualizado = tutorService.actualizar(uuid, tutorDTO);
        if (actualizado) {
            return ResponseEntity.ok("Tutor actualizado exitosamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "RF4.12 Cambiar el estatus de un Tutor (ACTIVO ↔ INACTIVO)")
    @PutMapping("/CambiarEstatus/{uuid}")
    public ResponseEntity<String> cambiarEstatus(@PathVariable String uuid) {
        boolean cambiado = tutorService.cambiarEstatus(uuid);
        if (cambiado) {
            return ResponseEntity.ok("Estatus del tutor actualizado correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un Tutor por UUID")
    @DeleteMapping("/Eliminar/{uuid}")
    public ResponseEntity<String> eliminar(@PathVariable String uuid) {
        boolean eliminado = tutorService.eliminar(uuid);
        if (eliminado) {
            return ResponseEntity.ok("Tutor eliminado exitosamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "RF4.13 Obtener lista de tutores activos (para combos)")
    @GetMapping("/combo")
    public List<TutorResumenDTO> obtenerCamposActivos() {
        return tutorService.obtenerActivos();
    }
    
    @PostMapping("/registrar")
    @Operation(
        summary = "Registrar relación Alumno–Tutor",
        description = "Crea una relación entre un alumno, un tutor y un ciclo escolar, incluyendo el parentesco."
    )
    public ResponseEntity<Map<String, Object>> registrarRelacion(@RequestBody AlumnoTutorDTO dto) {
        try {
            Map<String, Object> response = tutorService.guardarRelacion(dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

}
