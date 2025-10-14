package com.primaria.app.Service;


import com.primaria.app.DTO.AsignacionDocenteGradoGrupoDTO;
import com.primaria.app.DTO.AsignacionDocenteGradoGrupoResumenDTO;
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

import java.util.List;

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

        AsignacionDocenteGradoGrupo saved = repository.save(asignacion);
        return saved.getId(); // Solo regresamos el ID
    }


    public List<AsignacionDocenteGradoGrupoResumenDTO> filtrarAsignacionesResumen(String docenteId, String gradoId, String grupoId, String cicloId) {
        List<AsignacionDocenteGradoGrupo> resultado = repository.filtrar(docenteId, gradoId, grupoId, cicloId);

        if (resultado.isEmpty()) {
            throw new RuntimeException("No se encontraron asignaciones con los filtros proporcionados");
        }

        return resultado.stream().map(a -> {
            String nombreProfesor = a.getDocente() != null ? a.getDocente().getNombre() + " " + a.getDocente().getApellidos() : "";
            String rfc = a.getDocente() != null ? a.getDocente().getRfc() : "";
            String clave = a.getDocente() != null ? a.getDocente().getClavePresupuestal() : "";

            String nombreGrado = a.getGrado() != null ? a.getGrado().getNombre() : "";
            String nombreGrupo = a.getGrupo() != null ? a.getGrupo().getNombre() : "";
            String nombreCiclo = a.getCiclo() != null ? a.getCiclo().getFechaInicio() + "-" + a.getCiclo().getFechaFin() : "";

            return new AsignacionDocenteGradoGrupoResumenDTO(
                    a.getDocente() != null ? a.getDocente().getId() : "",
                    nombreProfesor,
                    rfc,
                    clave,
                    nombreGrado,
                    nombreGrupo,
                    nombreCiclo
            );
        }).toList();
    }

}
