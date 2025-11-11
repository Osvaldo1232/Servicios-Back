package com.primaria.app.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.primaria.app.DTO.CalificacionFinalDTO;
import com.primaria.app.Model.CalificacionFinalMateria;
import com.primaria.app.Model.Calificacion_final;
import com.primaria.app.Model.CicloEscolar;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.Grado;
import com.primaria.app.Model.Materia;
import com.primaria.app.Model.Trimestres;
import com.primaria.app.repository.CalificacionFinalMateriaRepository;
import com.primaria.app.repository.CalificacionFinalRepository;
import com.primaria.app.repository.CicloEscolaresRepository;
import com.primaria.app.repository.EstudianteRepository;
import com.primaria.app.repository.GradosRepository;
import com.primaria.app.repository.MateriasRepository;
import com.primaria.app.repository.TrimestreRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class CalificacionServiceAuto {

    @Autowired
    private CalificacionFinalRepository calificacionRepo;

    @Autowired
    private CalificacionFinalMateriaRepository calificacionFinalMateriaRepo;

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private MateriasRepository materiaRepo;

    @Autowired
    private TrimestreRepository trimestreRepo;

    @Autowired
    private CicloEscolaresRepository cicloRepo;

    @Autowired
    private GradosRepository gradoRepo;

    /**
     * Asigna o actualiza una calificación individual de trimestre
     */
    @Transactional
    public Calificacion_final asignarCalificacion(CalificacionFinalDTO dto) {
        Calificacion_final calificacion;

        if (dto.getId() != null && !dto.getId().isEmpty()) {
            // 🔁 Actualización
            calificacion = calificacionRepo.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Calificación no encontrada"));
        } else {
            // 🆕 Creación
            Estudiante alumno = estudianteRepo.findById(dto.getIdAlumno())
                    .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
            Materia materia = materiaRepo.findById(dto.getIdMateria())
                    .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
            Trimestres trimestre = trimestreRepo.findById(dto.getIdTrimestre())
                    .orElseThrow(() -> new RuntimeException("Trimestre no encontrado"));
            CicloEscolar ciclo = cicloRepo.findById(dto.getIdCicloEscolar())
                    .orElseThrow(() -> new RuntimeException("Ciclo escolar no encontrado"));
            Grado grado = gradoRepo.findById(dto.getIdGrado())
                    .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

            Optional<Calificacion_final> existente = calificacionRepo
                    .findByAlumnoIdAndMateriaIdAndTrimestreIdAndCicloId(
                            alumno.getId(), materia.getId(), trimestre.getId(), ciclo.getId()
                    );
            if (existente.isPresent()) {
                throw new RuntimeException("Ya existe una calificación para este alumno, materia, trimestre y ciclo");
            }

            calificacion = new Calificacion_final();
            calificacion.setAlumno(alumno);
            calificacion.setMateria(materia);
            calificacion.setTrimestre(trimestre);
            calificacion.setCiclo(ciclo);
            calificacion.setGrado(grado);
        }

        // 🎯 Asignar o actualizar promedio del trimestre
        calificacion.setPromedio(dto.getPromedio());

        Calificacion_final guardada = calificacionRepo.save(calificacion);

        // 🔁 Actualizar promedio final de materia
        actualizarPromedioMateria(guardada);

        return guardada;
    }

    /**
     * Asigna varias calificaciones en una sola operación
     */
    @Transactional
    public void asignarCalificaciones(List<CalificacionFinalDTO> calificaciones) {
        for (CalificacionFinalDTO dto : calificaciones) {
            asignarCalificacion(dto);
        }
    }

    /**
     * Calcula y actualiza el promedio final de una materia (CalificacionFinalMateria)
     */
    private void actualizarPromedioMateria(Calificacion_final calificacion) {
        String idAlumno = calificacion.getAlumno().getId();
        String idMateria = calificacion.getMateria().getId();
        String idCiclo = calificacion.getCiclo().getId();
        String idGrado = calificacion.getGrado().getId();

        // 📊 Obtener todas las calificaciones trimestrales del alumno para esa materia
        List<Calificacion_final> calificacionesTrimestrales =
                calificacionRepo.findAllByAlumnoAndMateriaAndCiclo(idAlumno, idMateria, idCiclo);

        if (calificacionesTrimestrales.isEmpty()) return;

        // 🧮 Calcular el promedio final
        double promedioFinal = calificacionesTrimestrales.stream()
                .mapToDouble(c -> c.getPromedio() != null ? c.getPromedio() : 0.0)
                .average()
                .orElse(0.0);

        BigDecimal promedioBD = BigDecimal.valueOf(promedioFinal).setScale(2, RoundingMode.HALF_UP);

        // 🔎 Buscar si ya existe el registro final
        Optional<CalificacionFinalMateria> existenteOpt =
                calificacionFinalMateriaRepo.findByAlumnoIdAndMateriaIdAndCicloEscolarId(idAlumno, idMateria, idCiclo);

        CalificacionFinalMateria registro;
        if (existenteOpt.isPresent()) {
            registro = existenteOpt.get();
            registro.setPromedio(promedioBD);
        } else {
            registro = new CalificacionFinalMateria(
                    calificacion.getAlumno(),
                    calificacion.getMateria(),
                    calificacion.getCiclo(),
                    calificacion.getGrado(),
                    promedioBD
            );
        }

        calificacionFinalMateriaRepo.save(registro);
    }
}
