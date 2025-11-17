package com.primaria.app.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.primaria.app.DTO.*;
import com.primaria.app.Model.*;
import com.primaria.app.exception.BusinessException;
import com.primaria.app.repository.*;

@Service
public class InscritoAlumnoService {

    @Autowired private AsignacionMateriaGradoRepository asignacionMateriaGradoRepository;
    @Autowired private InscritoAlumnoRepository inscritoAlumnoRepository;
    @Autowired private EstudianteRepository estudianteRepository;
  
    @Autowired private AlumnoTutorRepository alumnoTutorRepository;

    @Autowired private AsignacionDocenteGradoGrupoRepository asignacionRepository; // <- nuevo

  
  public InscritoAlumno guardarInscripcion(InscritoAlumnoDTO dto) {

    Estudiante alumno = estudianteRepository.findById(dto.getAlumnoId())
            .orElseThrow(() -> new BusinessException(1000, "Alumno no encontrado"));

    AsignacionDocenteGradoGrupo asignacion = asignacionRepository.findById(dto.getAsignacionId())
            .orElseThrow(() -> new BusinessException(1000, "Asignación no encontrada"));

    // 🔥 Validación: evitar inscripción duplicada
    inscritoAlumnoRepository.findByAlumno_IdAndAsignacion_Id(dto.getAlumnoId(), dto.getAsignacionId())
            .ifPresent(i -> {
                throw new BusinessException(1001, "El alumno ya está inscrito en esta asignación");
            });

    InscritoAlumno inscripcion = new InscritoAlumno();
    inscripcion.setAlumno(alumno);
    inscripcion.setAsignacion(asignacion);
    inscripcion.setFechaInscripcion(
            dto.getFechaInscripcion() != null ? dto.getFechaInscripcion() : LocalDateTime.now()
    );
    inscripcion.setEstatus(dto.getEstatus());

    return inscritoAlumnoRepository.save(inscripcion);
}



    public AlumnoInfoDTO obtenerInfoAlumno(String idAlumno) {
        InscritoAlumno inscripcion = inscritoAlumnoRepository
                .findTopByAlumno_IdOrderByAsignacion_Ciclo_AnioInicioDesc(idAlumno);
        if (inscripcion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El alumno no tiene inscripciones registradas");
        }

        Grado grado = inscripcion.getAsignacion() != null ? inscripcion.getAsignacion().getGrado() : null;
        if (grado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La inscripción no tiene grado asociado.");
        }

        List<MateriaCalificacionDTO> materias = asignacionMateriaGradoRepository.findByGradoId(grado.getId())
                .stream()
                .map(a -> new MateriaCalificacionDTO(
                        a.getMateria() != null ? safe(a.getMateria().getNombre()) : "",
                        null))
                .collect(Collectors.toList());

        String nombreGrado = safe(grado.getNombre());
        String nombreGrupo = safe(inscripcion.getAsignacion() != null && inscripcion.getAsignacion().getGrupo() != null
                ? inscripcion.getAsignacion().getGrupo().getNombre() : "");

        return new AlumnoInfoDTO(
                inscripcion.getAsignacion() != null && inscripcion.getAsignacion().getCiclo() != null
                        ? inscripcion.getAsignacion().getCiclo().getId() : null,
                nombreGrado,
                nombreGrupo,
                materias
        );
    }

    // ============================
    // Obtener última inscripción reciente
    // ============================
    public InscritoAlumnoRecienteDTO obtenerUltimoPorAlumno(String alumnoId) {
        InscritoAlumno inscrito = inscritoAlumnoRepository.findTopByAlumno_IdOrderByFechaInscripcionDesc(alumnoId);
        if (inscrito == null) return null;

        AsignacionDocenteGradoGrupo asign = inscrito.getAsignacion();
        CicloEscolar ciclo = asign != null ? asign.getCiclo() : null;
        Grado grado = asign != null ? asign.getGrado() : null;
        Grupo grupo = asign != null ? asign.getGrupo() : null;
        Profesor docente = asign != null ? asign.getDocente() : null;

        String cicloFormateado = null;
        if (ciclo != null && ciclo.getAnioInicio() != 0 && ciclo.getAnioFin() != 0) {
            cicloFormateado = ciclo.getAnioInicio() + "/" + ciclo.getAnioFin();
        }

        String nombreProfesorCompleto = null;
        String telefonoProfesor = null;
        if (docente != null) {
            nombreProfesorCompleto = String.join(" ",
                    safe(docente.getNombre()),
                    safe(docente.getApellidoPaterno()),
                    safe(docente.getApellidoMaterno())).trim();
            telefonoProfesor = safe(docente.getTelefono());
        }

        return new InscritoAlumnoRecienteDTO(
                inscrito.getId(),
                grado != null ? grado.getId() : null,
                grado != null ? grado.getNombre() : null,
                grupo != null ? grupo.getId() : null,
                grupo != null ? grupo.getNombre() : null,
                ciclo != null ? ciclo.getId() : null,
                cicloFormateado,
                nombreProfesorCompleto,
                telefonoProfesor
        );
    }

    // ============================
    // Info alumno-tutor
    // ============================
    public InfoAlumnoTutorDTO obtenerInfoPorAlumno(String idAlumno) {
        InscritoAlumno inscripcion = inscritoAlumnoRepository.findTopByAlumno_IdOrderByFechaInscripcionDesc(idAlumno);
        if (inscripcion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró inscripción para el alumno con ID: " + idAlumno);
        }

        String cicloId = inscripcion.getAsignacion() != null && inscripcion.getAsignacion().getCiclo() != null
                ? inscripcion.getAsignacion().getCiclo().getId() : null;

        AlumnoTutor relacion = alumnoTutorRepository.findByAlumno_IdAndCiclo_Id(idAlumno, cicloId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontró tutor asignado para el alumno en el ciclo actual."));

        Estudiante alumno = inscripcion.getAlumno();
        Tutor tutor = relacion.getTutor();
        Grado grado = inscripcion.getAsignacion() != null ? inscripcion.getAsignacion().getGrado() : null;
        Grupo grupo = inscripcion.getAsignacion() != null ? inscripcion.getAsignacion().getGrupo() : null;
        CicloEscolar ciclo = inscripcion.getAsignacion() != null ? inscripcion.getAsignacion().getCiclo() : null;

        String nombreAlumno = String.join(" ",
                safe(alumno.getNombre()),
                safe(alumno.getApellidoPaterno()),
                safe(alumno.getApellidoMaterno())).trim();

        String nombreTutor = (tutor != null)
                ? String.join(" ",
                    safe(tutor.getNombre()),
                    safe(tutor.getApellidoPaterno()),
                    safe(tutor.getApellidoMaterno())).trim()
                : "";

        String fechaCiclo = (ciclo != null && ciclo.getAnioInicio() != 0 && ciclo.getAnioFin() != 0)
                ? ciclo.getAnioInicio() + " - " + ciclo.getAnioFin()
                : "";

        return new InfoAlumnoTutorDTO(
                safe(alumno.getCurp()),
                nombreAlumno,
                nombreTutor,
                safe(alumno.getMatricula()),
                safe(grado != null ? grado.getNombre() : ""),
                safe(groupNameOrEmpty(grupo)),
                fechaCiclo
        );
    }

    private String groupNameOrEmpty(Grupo grupo) {
        return grupo != null ? safe(grupo.getNombre()) : "";
    }

    // ============================
    // Obtener por grado/grupo/ciclo -> ahora por asignacion
    // ============================
    public List<InscritoAlumnoInfoBasicaDTO> obtenerPorGradoGrupoCiclo(String gradoId, String grupoId, String cicloId) {

        List<InscritoAlumno> inscripciones =
                inscritoAlumnoRepository.findByAsignacion_Grado_IdAndAsignacion_Grupo_IdAndAsignacion_Ciclo_Id(
                        gradoId, grupoId, cicloId
                );

        return inscripciones.stream()
                .map(i -> {

                    var alumno = i.getAlumno();
                    var asign = i.getAsignacion();
                    var grado = asign != null ? asign.getGrado() : null;
                    var grupo = asign != null ? asign.getGrupo() : null;

                    String alumnoId = alumno != null ? alumno.getId() : null;

                    // 🔹 1) Buscar la última inscripción del alumno en general
                    InscritoAlumno ultima = inscritoAlumnoRepository
                            .findTopByAlumno_IdOrderByFechaInscripcionDesc(alumnoId);

                    boolean esUltima = (ultima != null && ultima.getId().equals(i.getId()));

                    // 🔹 2) Ver si el alumno tiene un tutor en este ciclo escolar
                    boolean tieneTutor = alumnoTutorRepository
                            .existsByAlumno_IdAndCiclo_Id(alumnoId, cicloId);

                    return new InscritoAlumnoInfoBasicaDTO(
                            alumnoId,
                            safe(alumno != null ? alumno.getNombre() : ""),
                            safe(alumno != null ? alumno.getApellidoPaterno() : ""),
                            safe(alumno != null ? alumno.getApellidoMaterno() : ""),
                            safe(alumno != null ? alumno.getMatricula() : ""),
                            safe(alumno != null ? alumno.getCurp() : ""),
                            grado != null ? grado.getId() : null,
                            safe(grado != null ? grado.getNombre() : ""),
                            grupo != null ? grupo.getId() : null,
                            safe(grupo != null ? grupo.getNombre() : ""),
                            i.getEstatus(),
                            esUltima,      // 🔥 Nuevo valor 1
                            tieneTutor     // 🔥 Nuevo valor 2
                    );
                })
                .collect(Collectors.toList());
    }


    // ============================
    // Obtener alumnos por ciclo (usando asignacion.ciclo)
    // ============================
    public List<AlumnoCargaDTO> obtenerAlumnosPorCiclo(String cicloId) {
        return inscritoAlumnoRepository.findByAsignacion_Ciclo_Id(cicloId)
                .stream()
                .map(inscrito -> {
                    var alumno = inscrito.getAlumno();
                    var asign = inscrito.getAsignacion();
                    var grado = asign != null ? asign.getGrado() : null;
                    var grupo = asign != null ? asign.getGrupo() : null;

                    var tutor = alumnoTutorRepository.findByAlumno_IdAndCiclo_Id(alumno.getId(), cicloId)
                            .map(AlumnoTutor::getTutor)
                            .orElse(null);

                    return new AlumnoCargaDTO(
                            safe(alumno.getNombre()),
                            safe(alumno.getApellidoPaterno()),
                            safe(alumno.getApellidoMaterno()),
                            safe(alumno.getMatricula()),
                            safe(alumno.getCurp()),
                            safe(grado != null ? grado.getNombre() : ""),
                            safe(grupo != null ? grupo.getNombre() : ""),
                            safe(tutor != null ? tutor.getNombre() : ""),
                            safe(tutor != null ? tutor.getApellidoPaterno() : ""),
                            safe(tutor != null ? tutor.getApellidoMaterno() : ""),
                            safe(tutor !=null ? tutor.getTelefono():"")
                    );
                })
                .collect(Collectors.toList());
    }

    // ============================
    // Obtener docentes por grado/grupo/ciclo (desde asignacion)
    // ============================
    public List<ProfesorRDTO> obtenerDocentesPorGradoGrupoYCiclo(String gradoId, String grupoId, String cicloId) {
        return inscritoAlumnoRepository.findByAsignacion_Grado_IdAndAsignacion_Grupo_IdAndAsignacion_Ciclo_Id(gradoId, grupoId, cicloId)
                .stream()
                .map(InscritoAlumno::getAsignacion)
                .filter(a -> a != null && a.getDocente() != null)
                .map(AsignacionDocenteGradoGrupo::getDocente)
                .distinct()
                .map(profesor -> new ProfesorRDTO(profesor.getId(), profesor.getNombre()))
                .collect(Collectors.toList());
    }

    // Helper
    private String safe(String s) {
        return s == null ? "" : s;
    }
    
    
    
    public List<AsignacionSelectDTO> obtenerAsignacionesSelect() {
        return asignacionRepository.findAll().stream()
            .map(asignacion -> {
                String nombreGrado = asignacion.getGrado() != null ? asignacion.getGrado().getNombre() : "";
                String nombreGrupo = asignacion.getGrupo() != null ? asignacion.getGrupo().getNombre() : "";
                String ciclo = asignacion.getCiclo() != null
                        ? asignacion.getCiclo().getAnioInicio() + "-" + asignacion.getCiclo().getAnioFin()
                        : "";

                String value = (nombreGrado + " " + nombreGrupo + " (" + ciclo + ")").trim();

                return new AsignacionSelectDTO(asignacion.getId(), value);
            })
            .collect(Collectors.toList());
    }
    
    public Optional<PerfilAlumnoDTO> obtenerPerfilAlumnoPorId(String idAlumno) {
        // Buscamos la inscripción actual del alumno
        Optional<InscritoAlumno> optInscrito = inscritoAlumnoRepository.findByAlumnoId(idAlumno);

        if (optInscrito.isEmpty()) return Optional.empty();

        var inscrito = optInscrito.get();
        var alumno = inscrito.getAlumno();
        var asignacion = inscrito.getAsignacion();
        var ciclo = asignacion.getCiclo();

        // 🔹 Buscar el tutor relacionado al mismo ciclo
        Optional<AlumnoTutor> optTutor = alumnoTutorRepository.findByAlumnoIdAndCicloId(alumno.getId(), ciclo.getId());

        String nombreTutor = "Sin asignar";
        String parentesco = "";

        if (optTutor.isPresent()) {
            var tutor = optTutor.get().getTutor();
            nombreTutor = tutor.getNombre() + " " + tutor.getApellidoPaterno() + " " + tutor.getApellidoMaterno();
            parentesco = optTutor.get().getParentesco();
        }

        String nombreAlumno = alumno.getNombre() + " " +
                              alumno.getApellidoPaterno() + " " +
                              alumno.getApellidoMaterno();

        String grado = asignacion.getGrado() != null ? asignacion.getGrado().getNombre() : "";
        String grupo = asignacion.getGrupo() != null ? asignacion.getGrupo().getNombre() : "";
    
        String nombreCiclo = (ciclo != null) 
        	    ? ciclo.getAnioInicio() + "-" + ciclo.getAnioFin()
        	    : "No definido";

        

        return Optional.of(new PerfilAlumnoDTO(
                alumno.getCurp(),
                alumno.getMatricula(),
                nombreAlumno.trim(),
                nombreTutor.trim(),
                parentesco,
                grado,
                grupo,
                nombreCiclo
        ));
    }
    
    
    public List<CicloAlumnosDTO> obtenerAlumnosSPorCiclo(String cicloId) {
        List<InscritoAlumno> inscritos = inscritoAlumnoRepository
                .findDistinctByAsignacion_Ciclo_IdAndEstatus(cicloId,  Estatus.ACTIVO);

        return inscritos.stream()
                .map(ia -> {
                    String nombreCompleto = ia.getAlumno().getNombre() + " " +
                                            ia.getAlumno().getApellidoPaterno() + " " +
                                            ia.getAlumno().getApellidoMaterno();
                    return new CicloAlumnosDTO(ia.getAlumno().getId(), nombreCompleto);
                })
                .collect(Collectors.toList());
    }
    
    
    public List<AlumnoInscritoDTO> obtenerAlumnosPorAsignacion(String idAsignacion) {
        return inscritoAlumnoRepository.listarAlumnosPorAsignacion(idAsignacion , Estatus.ACTIVO);
    }
}
