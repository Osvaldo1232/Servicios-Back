package com.primaria.app.Service;

import com.primaria.app.DTO.MateriaReprobadaDTO;
import com.primaria.app.DTO.ReprobadosDTO;
import com.primaria.app.repository.ReprobadosRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReprobadosService {

    private final ReprobadosRepository repo;

    public ReprobadosService(ReprobadosRepository repo) {
        this.repo = repo;
    }

    public List<ReprobadosDTO> obtenerReprobadosPorAsignacion(String idAsignacion) {

        List<Object[]> rows = repo.obtenerReprobadosPorAsignacion(idAsignacion);

        Map<String, ReprobadosDTO> agrupado = new HashMap<>();

        for (Object[] r : rows) {

            String idAlumno = (String) r[0];
            String nombreCompleto = (String) r[1];
            String grado = (String) r[2];
            String grupo = (String) r[3];
            String ciclo = (String) r[4];

            String materia = (String) r[5];
            java.math.BigDecimal promedio = (java.math.BigDecimal) r[6];

            MateriaReprobadaDTO materiaDTO = new MateriaReprobadaDTO(materia, promedio);

            agrupado.computeIfAbsent(idAlumno, k ->
                    new ReprobadosDTO(idAlumno, nombreCompleto, grado, grupo, ciclo, new ArrayList<>())
            ).getMaterias().add(materiaDTO);
        }

        return new ArrayList<>(agrupado.values());
    }
}
