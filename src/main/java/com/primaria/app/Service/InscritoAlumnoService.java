package com.primaria.app.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.primaria.app.DTO.InscritoAlumnoDTO;
import com.primaria.app.DTO.InscritoAlumnoDetalleDTO;
import com.primaria.app.Model.*;
import com.primaria.app.repository.*;

@Service
public class InscritoAlumnoService {

    @Autowired
    private InscritoAlumnoRepository inscritoAlumnoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private GradosRepository gradoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CicloEscolaresRepository cicloRepository;

    public InscritoAlumno guardarInscripcion(InscritoAlumnoDTO dto) {
        // Buscar entidades relacionadas por ID
        Estudiante alumno = estudianteRepository.findById(dto.getAlumnoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alumno no encontrado"));

        Profesor docente = profesorRepository.findById(dto.getDocenteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Docente no encontrado"));

        Grado grado = gradoRepository.findById(dto.getGradoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grado no encontrado"));

        Grupo grupo = grupoRepository.findById(dto.getGrupoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Grupo no encontrado"));

        CicloEscolar ciclo = cicloRepository.findById(dto.getCicloId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ciclo escolar no encontrado"));

        // Crear la inscripción
        InscritoAlumno inscripcion = new InscritoAlumno();
        inscripcion.setAlumno(alumno);
        inscripcion.setDocente(docente);
        inscripcion.setGrado(grado);
        inscripcion.setGrupo(grupo);
        inscripcion.setCiclo(ciclo);
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion() != null ? dto.getFechaInscripcion() : LocalDate.now());
        inscripcion.setEstatus(dto.getEstatus());

        // Guardar y devolver
        return inscritoAlumnoRepository.save(inscripcion);
    }
    
    
    public List<InscritoAlumnoDetalleDTO> filtrarInscripciones(String gradoId, String grupoId, String cicloId) {

        if ((gradoId == null || gradoId.isEmpty()) &&
            (grupoId == null || grupoId.isEmpty()) &&
            (cicloId == null || cicloId.isEmpty())) {
            throw new IllegalArgumentException("Debe proporcionar al menos un parámetro de búsqueda");
        }

        List<InscritoAlumno> inscripciones = inscritoAlumnoRepository.filtrar(gradoId, grupoId, cicloId);

        return inscripciones.stream().map(i -> {
        	String nombreAlumno = i.getAlumno().getNombre() + " " + i.getAlumno().getApellidos();

         
            String nombreProfesor = i.getDocente().getNombre() + " " + i.getDocente().getApellidos();

            
            
            String nombreGrado = i.getGrado() != null ? i.getGrado().getNombre() : "";
            String nombreGrupo = i.getGrupo() != null ? i.getGrupo().getNombre() : "";

            return new InscritoAlumnoDetalleDTO(
                    i.getId(),
                    i.getAlumno().getId(),
                    i.getAlumno().getMatricula(),
                    i.getAlumno().getCurp(),
                    nombreAlumno,
                    nombreGrado,
                    nombreGrupo,
                    nombreProfesor
            );
        }).collect(Collectors.toList());
    }
}
