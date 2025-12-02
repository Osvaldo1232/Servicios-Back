package com.primaria.app.Service;

import com.primaria.app.DTO.PromedioCampoFormativoDTO;
import com.primaria.app.DTO.ReporteAlumnoDTO;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.InscritoAlumno;
import com.primaria.app.repository.CalificacionFinalMateriaRepository;
import com.primaria.app.repository.CalificacionFinalRepository;
import com.primaria.app.repository.EstudianteRepository;
import com.primaria.app.repository.InscritoAlumnoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ReporteAlumnoService {


    @Autowired
    private CalificacionFinalRepository calificacionRepo;

    @Autowired
    private EstudianteRepository alumnoRepo;
    
    @Autowired
    private InscritoAlumnoRepository inscritoRepo;

    public ReporteAlumnoDTO obtenerReportePorAlumno(String idAlumno, String idCiclo) {

        Estudiante alumno = alumnoRepo.findById(idAlumno)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado."));

        InscritoAlumno inscrito = inscritoRepo.findByAlumnoIdAndCicloAndEstatus(idAlumno, idCiclo, Estatus.ACTIVO)
                .orElseThrow(() -> new RuntimeException("El alumno no está inscrito en este ciclo o no tiene inscripción activa."));

        String grado = inscrito.getAsignacion().getGrado().getNombre();
        String grupo = inscrito.getAsignacion().getGrupo().getNombre();

        List<PromedioCampoFormativoDTO> campos = calificacionRepo.obtenerPromediosPorCampoFormativo(idAlumno, idCiclo);

        BigDecimal promedioGeneral = campos.stream()
                .map(PromedioCampoFormativoDTO::getPromedioFinal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!campos.isEmpty()) {
            promedioGeneral = promedioGeneral.divide(BigDecimal.valueOf(campos.size()), 1, RoundingMode.HALF_UP);
        }

        return new ReporteAlumnoDTO(
                alumno.getId(),
                alumno.getNombre() + " " + alumno.getApellidoPaterno() + " " + alumno.getApellidoMaterno(),
                grado,
                grupo,
                campos,
                promedioGeneral
        );
    }
}
