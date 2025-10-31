package com.primaria.app.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.primaria.app.DTO.AlumnoInfoDTO;
import com.primaria.app.DTO.InfoAlumnoTutorDTO;
import com.primaria.app.DTO.InscritoAlumnoDTO;
import com.primaria.app.DTO.InscritoAlumnoDetalleDTO;
import com.primaria.app.DTO.InscritoAlumnoInfoBasicaDTO;
import com.primaria.app.DTO.InscritoAlumnoRecienteDTO;
import com.primaria.app.DTO.MateriaCalificacionDTO;
import com.primaria.app.Model.*;
import com.primaria.app.repository.*;

@Service
public class InscritoAlumnoService {

    @Autowired
    private AsignacionMateriaGradoRepository asignacionMateriaGradoRepository;

    @Autowired
    private InscritoAlumnoRepository inscritoAlumnoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private AlumnoTutorRepository alumnoTutorRepository;

    @Autowired
    private GradosRepository gradoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CicloEscolaresRepository cicloRepository;

    public InscritoAlumno guardarInscripcion(InscritoAlumnoDTO dto) {
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

        InscritoAlumno inscripcion = new InscritoAlumno();
        inscripcion.setAlumno(alumno);
        inscripcion.setDocente(docente);
        inscripcion.setGrado(grado);
        inscripcion.setGrupo(grupo);
        inscripcion.setCiclo(ciclo);
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion() != null ? dto.getFechaInscripcion() : LocalDateTime.now());
        inscripcion.setEstatus(dto.getEstatus());

        return inscritoAlumnoRepository.save(inscripcion);
    }

    
     

    public AlumnoInfoDTO obtenerInfoAlumno(String idAlumno) {
        InscritoAlumno inscripcion = inscritoAlumnoRepository.encontrarUltimaInscripcionPorAlumno(idAlumno);
        if (inscripcion == null) {
            throw new RuntimeException("El alumno no tiene inscripciones registradas");
        }

        String idGrado = inscripcion.getGrado() != null ? inscripcion.getGrado().getId() : null;
        if (idGrado == null) {
            throw new RuntimeException("La inscripción no tiene grado asociado.");
        }

        List<AsignacionMateriaGrado> asignaciones = asignacionMateriaGradoRepository.findByGradoId(idGrado);

        List<MateriaCalificacionDTO> materias = asignaciones.stream()
                .map(a -> new MateriaCalificacionDTO(
                        a.getMateria() != null ? safe(a.getMateria().getNombre()) : "",
                        null))
                .collect(Collectors.toList());

        String nombreGrado = inscripcion.getGrado() != null ? safe(inscripcion.getGrado().getNombre()) : "";
        String nombreGrupo = inscripcion.getGrupo() != null ? safe(inscripcion.getGrupo().getNombre()) : "";

        return new AlumnoInfoDTO(
                inscripcion.getCiclo() != null ? inscripcion.getCiclo().getId() : null,
                nombreGrado,
                nombreGrupo,
                materias
        );
    }

    public InscritoAlumnoRecienteDTO obtenerUltimoPorAlumno(String alumnoId) {
        InscritoAlumno inscrito = inscritoAlumnoRepository.findTopByAlumno_IdOrderByFechaInscripcionDesc(alumnoId);

        if (inscrito == null) return null;

        String cicloFormateado = null;
        if (inscrito.getCiclo() != null &&
            inscrito.getCiclo().getFechaInicio() != null &&
            inscrito.getCiclo().getFechaFin() != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            cicloFormateado = inscrito.getCiclo().getFechaInicio().format(fmt)
                    + "/" + inscrito.getCiclo().getFechaFin().format(fmt);
        }

        String nombreProfesorCompleto = null;
        String telefonoProfesor = null;
        if (inscrito.getDocente() != null) {
            var docente = inscrito.getDocente();
            nombreProfesorCompleto = String.join(" ",
                    safe(docente.getNombre()),
                    safe(docente.getApellidoPaterno()),
                    safe(docente.getApellidoMaterno())).trim();
            telefonoProfesor = safe(docente.getTelefono());
        }

        return new InscritoAlumnoRecienteDTO(
                inscrito.getId(),
                inscrito.getGrado() != null ? inscrito.getGrado().getId() : null,
                inscrito.getGrado() != null ? inscrito.getGrado().getNombre() : null,
                inscrito.getGrupo() != null ? inscrito.getGrupo().getId() : null,
                inscrito.getGrupo() != null ? inscrito.getGrupo().getNombre() : null,
                inscrito.getCiclo() != null ? inscrito.getCiclo().getId() : null,
                cicloFormateado,
                nombreProfesorCompleto,
                telefonoProfesor
        );
    }

    public InfoAlumnoTutorDTO obtenerInfoPorAlumno(String idAlumno) {
        InscritoAlumno inscripcion = inscritoAlumnoRepository.findTopByAlumno_IdOrderByFechaInscripcionDesc(idAlumno);
        if (inscripcion == null) {
            throw new RuntimeException("No se encontró inscripción para el alumno con ID: " + idAlumno);
        }

        AlumnoTutor relacion = alumnoTutorRepository.findByAlumno_IdAndCiclo_Id(
                idAlumno, inscripcion.getCiclo() != null ? inscripcion.getCiclo().getId() : null
        ).orElseThrow(() -> new RuntimeException(
                "No se encontró tutor asignado para el alumno en el ciclo actual."
        ));

        Estudiante alumno = inscripcion.getAlumno();
        Tutor tutor = relacion.getTutor();
        Grado grado = inscripcion.getGrado();
        Grupo grupo = inscripcion.getGrupo();
        CicloEscolar ciclo = inscripcion.getCiclo();

        String nombreAlumno = String.join(" ",
                safe(alumno != null ? alumno.getNombre() : null),
                safe(alumno != null ? alumno.getApellidoPaterno() : null),
                safe(alumno != null ? alumno.getApellidoMaterno() : null)).trim();

        String nombreTutor = "";
        if (tutor != null) {
            nombreTutor = String.join(" ",
                    safe(tutor.getNombre()),
                    safe(tutor.getApellidoPaterno()),
                    safe(tutor.getApellidoMaterno())).trim();
        }

        String fechaCiclo = "";
        if (ciclo != null) {
            String inicio = ciclo.getFechaInicio() != null ? ciclo.getFechaInicio().toString() : "";
            String fin = ciclo.getFechaFin() != null ? ciclo.getFechaFin().toString() : "";
            fechaCiclo = (inicio + " - " + fin).trim();
        }

        return new InfoAlumnoTutorDTO(
                alumno != null ? safe(alumno.getCurp()) : null,
                nombreAlumno,
                nombreTutor,
                alumno != null ? safe(alumno.getMatricula()) : null,
                grado != null ? safe(grado.getNombre()) : "",
                grupo != null ? safe(grupo.getNombre()) : "",
                fechaCiclo
        );
    }

    // ===== Helper =====
    private String safe(String s) {
        return s == null ? "" : s;
    }
    
    
    public List<InscritoAlumnoInfoBasicaDTO> obtenerPorGradoGrupoCiclo(String gradoId, String grupoId, String cicloId) {
        List<InscritoAlumno> inscripciones = inscritoAlumnoRepository
            .findByGrado_IdAndGrupo_IdAndCiclo_Id(gradoId, grupoId, cicloId);

        return inscripciones.stream()
                .map(i -> {
                    var alumno = i.getAlumno();
                    var grado = i.getGrado();
                    var grupo = i.getGrupo();

                    return new InscritoAlumnoInfoBasicaDTO(
                            alumno != null ? alumno.getId() : null,
                            alumno != null ? alumno.getNombre() : "",
                            alumno != null ? alumno.getApellidoPaterno() : "",
                            alumno != null ? alumno.getApellidoMaterno() : "",
                            alumno != null ? alumno.getMatricula() : "",
                            alumno != null ? alumno.getCurp() : "",
                            grado != null ? grado.getId() : null,
                            grado != null ? grado.getNombre() : "",
                            grupo != null ? grupo.getId() : null,
                            grupo != null ? grupo.getNombre() : "",
                            i.getEstatus()
                    );
                })
                .toList();
    }

}
