package com.primaria.app.Service;

import com.primaria.app.DTO.CalificacionFinalMateriaDTO;
import com.primaria.app.DTO.CalificacionMateriaDTO;
import com.primaria.app.DTO.CalificacionTotalAlumnoDTO;
import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.DTO.PromedioCampoDTO;
import com.primaria.app.DTO.PromedioGradoCicloDTO;
import com.primaria.app.Model.*;
import com.primaria.app.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private GradosRepository gradoRepo;

    // 🔹 Crear nueva calificación final
    public CalificacionFinalMateriaDTO crearCalificacion(CalificacionFinalMateriaDTO dto) {
        Estudiante alumno = alumnoRepo.findById(dto.getAlumnoId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        Materia materia = materiaRepo.findById(dto.getMateriaId())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        CicloEscolar ciclo = cicloRepo.findById(dto.getCicloEscolarId())
                .orElseThrow(() -> new RuntimeException("Ciclo escolar no encontrado"));
        Grado grado = gradoRepo.findById(dto.getGradoId())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

        CalificacionFinalMateria entidad = new CalificacionFinalMateria(
                alumno, materia, ciclo, grado, dto.getPromedio()
        );

        calificacionRepo.save(entidad);

        return new CalificacionFinalMateriaDTO(
                entidad.getId(),
                alumno.getId(),
                materia.getId(),
                ciclo.getId(),
                grado.getId(),
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
                        c.getGrado().getId(),
                        c.getPromedio(),
                        c.getFechaCreacion()
                ));
    }

    // 🔹 Obtener calificaciones de un alumno por ciclo
    public List<MateriaCalificacionResDTO> obtenerCalificacionesPorAlumnoYciclo(String alumnoId, String cicloId) {
        List<CalificacionFinalMateria> calificaciones = calificacionRepo
                .findByAlumno_IdAndCicloEscolar_Id(alumnoId, cicloId);

        return calificaciones.stream()
                .map(c -> new MateriaCalificacionResDTO(
                        c.getMateria().getId(),
                        c.getMateria().getNombre(),
                        c.getPromedio(),
                        c.getGrado().getId(),
                        c.getGrado().getNombre(),
                        c.getMateria().getCampoFormativo().getNombre()
                ))
                .collect(Collectors.toList());
    }
    
    public List<CalificacionTotalAlumnoDTO> getPromedioPorCicloYDocente(String cicloId, String docenteId) {

        List<Object[]> resultados = calificacionRepo.obtenerPromedioPorCicloYDocente(cicloId, docenteId);

        return resultados.stream().map(r -> new CalificacionTotalAlumnoDTO(
                (String) r[0],  // idAlumno
                (String) r[1],  // nombreAlumno
                (String) r[2],  // grado
                (String) r[3],  // grupo
                (String) r[4],  // ciclo
                new BigDecimal(r[5].toString())  // calificacionTotal
        )).collect(Collectors.toList());
    }
public List<PromedioGradoCicloDTO> obtenerPromedios(String alumnoId) {
    List<CalificacionFinalMateria> lista = calificacionRepo.findByAlumnoId(alumnoId);

    if (lista.isEmpty()) {
        return Collections.emptyList();
    }

    return lista.stream()
        .collect(Collectors.groupingBy(
            cf -> cf.getGrado().getId() + "-" + cf.getCicloEscolar().getId(),
            Collectors.collectingAndThen(
                Collectors.toList(),
                group -> {
                    CalificacionFinalMateria muestra = group.get(0);

                    // Calcular promedio con BigDecimal y redondear a 2 decimales
                    BigDecimal promedio = group.stream()
                        .map(CalificacionFinalMateria::getPromedio)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(new BigDecimal(group.size()), 2, RoundingMode.HALF_UP);

                    return new PromedioGradoCicloDTO(
                        muestra.getGrado().getId(),
                        muestra.getGrado().getNombre(),
                        muestra.getCicloEscolar().getId(),
                        muestra.getCicloEscolar().getAnioInicio() + "-" + muestra.getCicloEscolar().getAnioFin(),
                        promedio
                    );
                }
            )
        ))
        .values()
        .stream()
        // 🔹 Ordenar por año de inicio del ciclo (más antiguo primero)
        .sorted(Comparator.comparing(dto -> Integer.parseInt(dto.getCicloEscolar().split("-")[0])))
        .toList();
}



public List<PromedioCampoDTO> obtenerPromediosPorCampo(String idCiclo) {
    return calificacionRepo.obtenerPromedioPorCampo(idCiclo)
            .stream()
            .map(obj -> new PromedioCampoDTO(
                    (String) obj[0],
                    obj[1] != null ? ((Number) obj[1]).doubleValue() : null
            ))
            .collect(Collectors.toList());
}

public Double obtenerPromedioGeneral(String idCiclo) {
    return calificacionRepo.obtenerPromedioGeneral(idCiclo);
}
public Map<String, Object> obtenerPromediosPorAlumno(String idAlumno) {
    List<CalificacionMateriaDTO> materias = calificacionRepo.obtenerPromediosPorAlumno(idAlumno);

    // Agrupar por grado
    Map<String, List<CalificacionMateriaDTO>> agrupadoPorGrado = new LinkedHashMap<>();
    for (CalificacionMateriaDTO dto : materias) {
        agrupadoPorGrado
            .computeIfAbsent(dto.getNombreGrado(), k -> new ArrayList<>())
            .add(dto);
    }

    // Calcular promedio total por grado
    List<Map<String, Object>> resultado = new ArrayList<>();
    for (Map.Entry<String, List<CalificacionMateriaDTO>> entry : agrupadoPorGrado.entrySet()) {
        String nombreGrado = entry.getKey();
        List<CalificacionMateriaDTO> lista = entry.getValue();

        BigDecimal total = lista.stream()
                .map(CalificacionMateriaDTO::getPromedio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal promedioGrado = total.divide(
                BigDecimal.valueOf(lista.size()), 2, RoundingMode.HALF_UP);

        Map<String, Object> gradoData = new LinkedHashMap<>();
        gradoData.put("grado", nombreGrado);
        gradoData.put("materias", lista);
        gradoData.put("promedioFinalGrado", promedioGrado);

        resultado.add(gradoData);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("idAlumno", idAlumno);
    response.put("calificacionesPorGrado", resultado);
    return response;
}
}
