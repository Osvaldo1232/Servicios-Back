package com.primaria.app.Service;

import com.primaria.app.DTO.ActividadAlumnoDTO;
import com.primaria.app.Model.Actividad;
import com.primaria.app.Model.ActividadAlumno;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.repository.ActividadAlumnoRepository;

import com.primaria.app.repository.EstudianteRepository;
import com.primaria.app.repository.actividadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadAlumnoService {

    @Autowired
    private ActividadAlumnoRepository repo;
    @Autowired
    private actividadRepository actividadRepo;
    @Autowired
    private EstudianteRepository estudianteRepo;

    // Asignar actividad a un alumno
    public ActividadAlumno asignarActividad(ActividadAlumnoDTO dto) {
        Actividad actividad = actividadRepo.findById(dto.getActividadId())
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        Estudiante alumno = estudianteRepo.findById(dto.getAlumnoId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        ActividadAlumno aa = new ActividadAlumno();
        aa.setActividad(actividad);
        aa.setAlumno(alumno);
        aa.setValorObtenido(dto.getValorObtenido());

        return repo.save(aa);
    }

    // Obtener actividades de un alumno
    public List<ActividadAlumno> obtenerPorAlumno(String alumnoId) {
        return repo.findByAlumnoId(alumnoId);
    }

    // Obtener actividades por actividad (ej. para calcular promedios)
    public List<ActividadAlumno> obtenerPorActividad(String actividadId) {
        return repo.findByActividadId(actividadId);
    }

    // Calcular promedio de un alumno en un conjunto de actividades
    public double calcularPromedio(List<ActividadAlumno> lista) {
        if (lista.isEmpty()) return 0.0;
        double suma = lista.stream().mapToDouble(ActividadAlumno::getValorObtenido).sum();
        double total = lista.stream().mapToDouble(a -> a.getActividad().getValor()).sum();
        return (suma / total) * 10; // escala de 0 a 10
    }
}
