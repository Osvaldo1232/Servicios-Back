package com.primaria.app.Service;



import com.primaria.app.DTO.CalificacionAlumnoDTO;
import com.primaria.app.repository.CalificacionAlumnosGradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionAlumnosGradoService {

    @Autowired
    private CalificacionAlumnosGradoRepository repository;

    public List<CalificacionAlumnoDTO> obtenerCalificaciones(String idCiclo, String idGrado, String idMateria) {
        List<Object[]> resultados = repository.obtenerCalificacionesAgrupadas(idCiclo, idGrado, idMateria);

        return resultados.stream().map(obj -> new CalificacionAlumnoDTO(
                (String) obj[0],          // nombre_materia
                (String) obj[1],          // id_alumno
                (String) obj[2],          // nombre_alumno
                (String) obj[3],          // id_trimestre_1
                (String) obj[4],          // id_calificacion_1
                obj[5] != null ? ((Number) obj[5]).doubleValue() : null, // trimestre_1
                (String) obj[6],          // id_trimestre_2
                (String) obj[7],          // id_calificacion_2
                obj[8] != null ? ((Number) obj[8]).doubleValue() : null, // trimestre_2
                (String) obj[9],          // id_trimestre_3
                (String) obj[10],         // id_calificacion_3
                obj[11] != null ? ((Number) obj[11]).doubleValue() : null, // trimestre_3
                obj[12] != null ? ((Number) obj[12]).doubleValue() : null  // promedio_final
        )).collect(Collectors.toList());

    }
}
