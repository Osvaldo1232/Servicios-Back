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
                (String) obj[0],  // nombre_materia
                (String) obj[1],  // id_alumno
                (String) obj[2],  // nombre_alumno
                (String) obj[3],  // id_trimestre_1
                obj[4] != null ? ((Number) obj[4]).doubleValue() : null,  // trimestre_1
                (String) obj[5],  // id_trimestre_2
                obj[6] != null ? ((Number) obj[6]).doubleValue() : null,  // trimestre_2
                (String) obj[7],  // id_trimestre_3
                obj[8] != null ? ((Number) obj[8]).doubleValue() : null,  // trimestre_3
                obj[9] != null ? ((Number) obj[9]).doubleValue() : null   // promedio_final
        )).collect(Collectors.toList());
    }
}
