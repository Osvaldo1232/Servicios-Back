package com.primaria.app.Service;

import com.primaria.app.DTO.ActividadDTO;
import com.primaria.app.Model.*;
import com.primaria.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private actividadRepository actividadRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private AsignacionMateriaGradoRepository asignacionRepository;

    @Autowired
    private TrimestreRepository trimestreRepository;

    @Autowired
    private TipoEvaluacionRepository tipoEvaluacionRepository;

    // Guardar actividad
    public String guardarActividad(ActividadDTO dto) {
        Profesor docente = profesorRepository.findById(dto.getDocenteId()).orElse(null);
        AsignacionMateriaGrado asignacion = asignacionRepository.findById(dto.getAsignacionMateriaGradoId()).orElse(null);
        Trimestres trimestre = trimestreRepository.findById(dto.getTrimestreId()).orElse(null);
        Tipos_Evaluacion tipoEvaluacion = tipoEvaluacionRepository.findById(dto.getTipoEvaluacionId()).orElse(null);

        if (docente == null || asignacion == null || trimestre == null || tipoEvaluacion == null) {
            throw new RuntimeException("Alguna de las referencias no existe");
        }

        Actividad actividad = new Actividad();
        actividad.setDocente(docente);
        actividad.setAsignacionMateriaGrado(asignacion);
        actividad.setTrimestre(trimestre);
        actividad.setTipoEvaluacion(tipoEvaluacion);
        actividad.setNombre(dto.getNombre());
        actividad.setFecha(dto.getFecha());
        actividad.setValor(dto.getValor());

        Actividad saved = actividadRepository.save(actividad);
        return saved.getId();
    }

    // Filtrar actividades
    public List<Actividad> filtrarActividades(String docenteId, String asignacionId, String trimestreId, String tipoEvaluacionId) {
        List<Actividad> resultado = actividadRepository.filtrar(docenteId, asignacionId, trimestreId, tipoEvaluacionId);

        if (resultado.isEmpty()) {
            throw new RuntimeException("No se encontraron actividades con los filtros proporcionados");
        }
        return resultado;
    }
}
