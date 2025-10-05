package com.primaria.app.Service;


import com.primaria.app.DTO.CalificacionDTO;
import com.primaria.app.Model.Actividad;
import com.primaria.app.Model.Calificacion;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.CicloEscolar;

import com.primaria.app.repository.CalificacionRepository;
import com.primaria.app.repository.CicloEscolaresRepository;
import com.primaria.app.repository.EstudianteRepository;

import com.primaria.app.repository.actividadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private actividadRepository actividadRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private CicloEscolaresRepository cicloRepository;

    // Guardar calificación
    public Calificacion guardarCalificacion(CalificacionDTO dto) {
        Actividad actividad = actividadRepository.findById(dto.getActividadId())
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        Estudiante alumno = estudianteRepository.findById(dto.getAlumnoId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        CicloEscolar ciclo = cicloRepository.findById(dto.getCicloId())
                .orElseThrow(() -> new RuntimeException("Ciclo escolar no encontrado"));

        Calificacion cal = new Calificacion();
        cal.setActividad(actividad);
        cal.setAlumno(alumno);
        cal.setCiclo(ciclo);
        cal.setCalificacion(dto.getCalificacion());

        return calificacionRepository.save(cal);
    }

    // Filtrar calificaciones
    public List<Calificacion> filtrarCalificaciones(String alumnoId, String materiaId, String trimestreId, String cicloId) {
        List<Calificacion> lista = calificacionRepository.filtrar(alumnoId, materiaId, trimestreId, cicloId);

        if (lista.isEmpty()) {
            throw new RuntimeException("No se encontraron calificaciones con los filtros proporcionados");
        }

        return lista;
    }

    // Promedio por materia y ciclo
    public Double calcularPromedio(String alumnoId, String materiaId, String cicloId) {
        List<Calificacion> lista = calificacionRepository.filtrar(alumnoId, materiaId, null, cicloId);
        if (lista.isEmpty()) return 0.0;

        return lista.stream()
                .mapToDouble(Calificacion::getCalificacion)
                .average()
                .orElse(0.0);
    }
}
