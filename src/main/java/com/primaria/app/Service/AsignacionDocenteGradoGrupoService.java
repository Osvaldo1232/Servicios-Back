package com.primaria.app.Service;

import com.primaria.app.DTO.AsignacionDocenteGradoGrupoDTO;
import com.primaria.app.DTO.AsignacionDocenteGradoGrupoResumenDTO;
import com.primaria.app.DTO.AsignacionGradoGrupoCicloDTO;
import com.primaria.app.Model.AsignacionDocenteGradoGrupo;

import com.primaria.app.Model.Grado;
import com.primaria.app.Model.Grupo;
import com.primaria.app.Model.Profesor;
import com.primaria.app.Model.CicloEscolar;
import com.primaria.app.Model.Estatus;
import com.primaria.app.repository.AsignacionDocenteGradoGrupoRepository;
import com.primaria.app.repository.CicloEscolaresRepository;
import com.primaria.app.repository.GradosRepository;
import com.primaria.app.repository.GrupoRepository;
import com.primaria.app.repository.ProfesorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignacionDocenteGradoGrupoService {

    @Autowired
    private AsignacionDocenteGradoGrupoRepository repository;

    @Autowired
    private ProfesorRepository docenteRepository;

    @Autowired
    private GradosRepository gradoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CicloEscolaresRepository cicloRepository;

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public String guardarAsignacion(AsignacionDocenteGradoGrupoDTO dto) {
        Profesor docente = docenteRepository.findById(dto.getIdDocente()).orElse(null);
        Grado grado = gradoRepository.findById(dto.getIdGrado()).orElse(null);
        Grupo grupo = grupoRepository.findById(dto.getIdGrupo()).orElse(null);
        CicloEscolar ciclo = cicloRepository.findById(dto.getIdCiclo()).orElse(null);

        if (docente == null || grado == null || grupo == null || ciclo == null) {
            throw new RuntimeException("Docente, grado, grupo o ciclo no encontrados");
        }

        AsignacionDocenteGradoGrupo asignacion = new AsignacionDocenteGradoGrupo();
        asignacion.setDocente(docente);
        asignacion.setGrado(grado);
        asignacion.setGrupo(grupo);
        asignacion.setCiclo(ciclo);
        asignacion.setEstatus(Estatus.ACTIVO);

        // No seteamos fechaCreado: @CreationTimestamp se encargará de eso
        AsignacionDocenteGradoGrupo saved = repository.save(asignacion);
        return saved.getId(); // Solo regresamos el ID
    }


    public List<AsignacionDocenteGradoGrupoResumenDTO> obtenerPorCiclo(String cicloId) {
        return repository.findByCiclo_Id(cicloId).stream()
                .map(a -> {
                    var docente = a.getDocente();
                    var grado = a.getGrado();
                    var grupo = a.getGrupo();
                    var ciclo = a.getCiclo();

                    return new AsignacionDocenteGradoGrupoResumenDTO(
                            docente != null ? docente.getId() : null,
                            docente != null ? docente.getNombre() : "",
                            docente != null ? docente.getRfc() : "",
                            docente != null ? docente.getClavePresupuestal() : "",
                            grado != null ? grado.getNombre() : "",
                            grupo != null ? grupo.getNombre() : "",
                            ciclo != null ? ciclo.getId() : "",
                            a.getFechaCreado() != null ? a.getFechaCreado().toString() : null
                    );
                })
                .collect(Collectors.toList());
    }

    
    public AsignacionGradoGrupoCicloDTO obtenerMasRecientePorProfesor(String idProfesor) {
        // Filtrar solo asignaciones del profesor dado
        List<AsignacionDocenteGradoGrupo> asignaciones = repository.findAll().stream()
                .filter(a -> a.getDocente() != null && a.getDocente().getId().equals(idProfesor))
                .toList();

        if (asignaciones.isEmpty()) {
            return null; // o lanza excepción si prefieres
        }

        // Tomar la más reciente según fechaCreado
        AsignacionDocenteGradoGrupo masReciente = asignaciones.stream()
                .max(Comparator.comparing(AsignacionDocenteGradoGrupo::getFechaCreado))
                .orElseThrow();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String cicloFormateado = "";
        if (masReciente.getCiclo() != null) {
            cicloFormateado = masReciente.getCiclo().getFechaInicio().format(formatter)
                             + "-" + masReciente.getCiclo().getFechaFin().format(formatter);
        }

        return new AsignacionGradoGrupoCicloDTO(
                masReciente.getGrado() != null ? masReciente.getGrado().getId() : "",
                masReciente.getGrado() != null ? masReciente.getGrado().getNombre() : "",
                masReciente.getGrupo() != null ? masReciente.getGrupo().getId() : "",
                masReciente.getGrupo() != null ? masReciente.getGrupo().getNombre() : "",
                masReciente.getCiclo() != null ? masReciente.getCiclo().getId() : "",
                cicloFormateado
        );
    }

}