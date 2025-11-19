package com.primaria.app.controller;

import java.util.List;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.primaria.app.DTO.CicloAlumnosDTO;
import com.primaria.app.DTO.EstudianteDTO;

import com.primaria.app.Model.Estudiante;

import com.primaria.app.Model.Usuario;
import com.primaria.app.Service.EstudianteService;
import com.primaria.app.Service.InscritoAlumnoService;
import com.primaria.app.Service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/alumnos")
@Tag(name = "alumnos", description = "Operaciones para registrar usuarios de distintos roles")
public class AlumnoController {

	
    private final EstudianteService alumnoService;

    public AlumnoController(EstudianteService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @Autowired
    private  InscritoAlumnoService inscritoAlumnoService;
    @Autowired
    private  UsuarioService usuarioService;
	
    @GetMapping("/usuario/{id}")
    @Operation(summary = "RF3.6:  Obtener  Estudiante")
    public ResponseEntity<?> getAlumnoPorUsuarioId(@PathVariable String id) {
        return alumnoService.findById(id)
                .map(alumno -> ResponseEntity.ok().body(alumno))
                .orElse(ResponseEntity.notFound().build());
    }
    
    
    @GetMapping("/todosestudiantes")
    @Operation(summary = "RF4.8 Obtener todos los estudiantes")
    public List<EstudianteDTO> obtenerTodosEstudiantes() {
        return alumnoService.listarTodos();
    }
    @Operation(summary = "Buscar estudiante por matrícula")
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<?> buscarPorMatricula(@PathVariable String matricula) {
        Estudiante estudiante = alumnoService.buscarPorMatricula(matricula);
        return ResponseEntity.ok(estudiante);
    }

    @Operation(summary = "Eliminar estudiante por matrícula")
    @DeleteMapping("/matricula/{matricula}")
    public ResponseEntity<?> eliminarPorMatricula(@PathVariable String matricula) {
    	alumnoService.eliminarPorMatricula(matricula);
        return ResponseEntity.ok("Estudiante eliminado correctamente");
    }
    @DeleteMapping("/id/{id}")
    @Operation(summary = "Eliminar estudiante por ID")
    public ResponseEntity<String> eliminarPorId(@PathVariable String id) {
        try {
        	alumnoService.eliminarPorId(id);
            return ResponseEntity.ok("Estudiante con ID '" + id + "' eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
  
    
    @PutMapping("/alumno/{id}")
    @Operation(summary = "RF4.6:  Actualizar alumno")
    public ResponseEntity<?> actualizarProfesor(@PathVariable String id, @RequestBody EstudianteDTO dto) {
        Optional<Usuario> optUsuario = usuarioService.findById(id);
        if (optUsuario.isEmpty() || !(optUsuario.get() instanceof Estudiante)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado");
        }
        Estudiante estudiante = (Estudiante) optUsuario.get();
        estudiante.setNombre(dto.getNombre());
        estudiante.setApellidoMaterno(dto.getApellidoMaterno());
        estudiante.setApellidoPaterno(dto.getApellidoPaterno());
        estudiante.setEmail(dto.getEmail());
        estudiante.setFechaNacimiento(dto.getFechaNacimiento());
        estudiante.setSexo(dto.getSexo());
        estudiante.setCurp(dto.getCurp());
        estudiante.setMatricula(dto.getMatricula());
      
        
        usuarioService.update(estudiante, dto.getPassword());

        return ResponseEntity.ok("Estudiante actualizado exitosamente");
    }
    
    @GetMapping("/por-ciclo/{cicloId}")
    @Operation(summary = "RF2.10 - Obtener lista de alumnos por ciclo escolar para el requ  RF2.10: ")
    public ResponseEntity<List<CicloAlumnosDTO>> obtenerAlumnosPorCiclo(@PathVariable String cicloId) {
        List<CicloAlumnosDTO> alumnos = inscritoAlumnoService.obtenerAlumnosSPorCiclo(cicloId);
        return ResponseEntity.ok(alumnos);
    }
    @GetMapping("/por-ciclo-docente")
    @Operation(summary = " RF2.10  Obtener lista de alumnos por ciclo escolar y docente")
    public ResponseEntity<List<CicloAlumnosDTO>> obtenerAlumnosPorCicloYDocente(
            @RequestParam String cicloId,
            @RequestParam String docenteId) {

        List<CicloAlumnosDTO> alumnos =
                inscritoAlumnoService.obtenerAlumnosPorCicloYDocente(cicloId, docenteId);

        return ResponseEntity.ok(alumnos);
    }
}
