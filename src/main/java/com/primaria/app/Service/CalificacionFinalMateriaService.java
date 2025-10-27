package com.primaria.app.Service;

import com.primaria.app.DTO.CalificacionFinalMateriaDTO;
import com.primaria.app.Model.*;
import com.primaria.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalificacionFinalMateriaService {

    @Autowired
    private CalificacionFinalMateriaRepository calificacionRepo;

    @Autowired
    private EstudianteRepository alumnoRepo;

    @Autowired
    private MateriasRepository materiaRepo;

    @Autowired
    private CicloEscolaresRepository cicloRepo;

    // 🔹 Crear nueva calificación final
    public CalificacionFinalMateriaDTO crearCalificacion(CalificacionFinalMateriaDTO dto) {
        Estudiante alumno = alumnoRepo.findById(dto.getAlumnoId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        Materia materia = materiaRepo.findById(dto.getMateriaId())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        CicloEscolar ciclo = cicloRepo.findById(dto.getCicloEscolarId())
                .orElseThrow(() -> new RuntimeException("Ciclo escolar no encontrado"));

        CalificacionFinalMateria entidad = new CalificacionFinalMateria(
                alumno, materia, ciclo, dto.getPromedio()
        );

        calificacionRepo.save(entidad);

        return new CalificacionFinalMateriaDTO(
                entidad.getId(),
                alumno.getId(),
                materia.getId(),
                ciclo.getId(),
                entidad.getPromedio(),
                entidad.getFechaCreacion()
        );
    }

    // 🔹 Consultar una calificación por ID
    public Optional<CalificacionFinalMateriaDTO> obtenerPorId(String id) {
        return calificacionRepo.findById(id)
                .map(c -> new CalificacionFinalMateriaDTO(
                        c.getId(),
                        c.getAlumno().getId(),
                        c.getMateria().getId(),
                        c.getCicloEscolar().getId(),
                        c.getPromedio(),
                        c.getFechaCreacion()
                ));
    }
}
