package com.primaria.app.Service;

import com.primaria.app.DTO.MateriaReprobadaDTO;
import com.primaria.app.DTO.PromedioAlumnoDTO;
import com.primaria.app.DTO.ReprobadosDTO;
import com.primaria.app.DTO.ResumenCalificacionesAsignacionDTO;
import com.primaria.app.repository.EstadisticasRepository;
import com.primaria.app.repository.EstudianteRepository;
import com.primaria.app.repository.ReprobadosRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;



@Service
public class ReprobadosService {
	private final EstudianteRepository estudianteRepo;
    private final ReprobadosRepository repo;
    private final EstadisticasRepository esta;
    public ReprobadosService(ReprobadosRepository repo, EstadisticasRepository esta, EstudianteRepository estudianteRepo) {
        this.repo = repo;
        this.esta=esta;
        this.estudianteRepo = estudianteRepo;
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
    
    
    public ResumenCalificacionesAsignacionDTO generarEstadisticas(String idAsignacion) {

        List<String> idsAlumnos = esta.obtenerAlumnosInscritos(idAsignacion);

        if (idsAlumnos.isEmpty()) {
            return new ResumenCalificacionesAsignacionDTO(0, 0, 0, new ArrayList<>());
        }

        List<Object[]> rows = esta.obtenerPromediosPorAlumno(idsAlumnos);

        Map<String, List<BigDecimal>> mapaCalificaciones = new HashMap<>();

        for (Object[] r : rows) {
            String idAlumno = (String) r[0];
            BigDecimal promedio = (BigDecimal) r[1];

            mapaCalificaciones
                    .computeIfAbsent(idAlumno, x -> new ArrayList<>())
                    .add(promedio);
        }

        List<PromedioAlumnoDTO> alumnosDTO = new ArrayList<>();
        int aprobados = 0;
        int reprobados = 0;

        for (String idAlumno : mapaCalificaciones.keySet()) {

            List<BigDecimal> califs = mapaCalificaciones.get(idAlumno);

            BigDecimal suma = califs.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal promedioGeneral = suma.divide(
                    BigDecimal.valueOf(califs.size()), 2, RoundingMode.HALF_UP);

            // obtener nombre
            var alumno = estudianteRepo.findById(idAlumno).orElse(null);
            String nombre = alumno.getNombre() + " " +
                    alumno.getApellidoPaterno() + " " +
                    alumno.getApellidoMaterno();

            alumnosDTO.add(new PromedioAlumnoDTO(idAlumno, nombre, promedioGeneral));

            if (promedioGeneral.compareTo(BigDecimal.valueOf(6)) < 0)
                reprobados++;
            else
                aprobados++;
        }

        return new ResumenCalificacionesAsignacionDTO(
                idsAlumnos.size(),
                aprobados,
                reprobados,
                alumnosDTO
        );
    }
}
