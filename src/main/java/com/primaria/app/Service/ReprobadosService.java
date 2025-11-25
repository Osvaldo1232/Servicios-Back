package com.primaria.app.Service;

import com.primaria.app.DTO.AlumnoMateriasDTO;
import com.primaria.app.DTO.MateriaCalificacionDTO;
import com.primaria.app.DTO.MateriaReprobadaDTO;
import com.primaria.app.DTO.MateriasCalificacionDTO;
import com.primaria.app.DTO.PromedioAlumnoDTO;
import com.primaria.app.DTO.ReprobadosDTO;
import com.primaria.app.DTO.ResumenCalificacionesAsignacionDTO;
import com.primaria.app.Model.CalificacionFinalMateria;
import com.primaria.app.Model.InscritoAlumno;
import com.primaria.app.repository.CalificacionFinalMateriaRepository;
import com.primaria.app.repository.EstadisticasRepository;
import com.primaria.app.repository.EstudianteRepository;
import com.primaria.app.repository.InscritoAlumnoRepository;
import com.primaria.app.repository.ReprobadosRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private InscritoAlumnoRepository inscritoAlumnoRepository;

    @Autowired
    private CalificacionFinalMateriaRepository calificacionRepository;
    
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
            BigDecimal promedio = ((BigDecimal) r[6])
                    .setScale(1, RoundingMode.HALF_UP);

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
    
    public List<AlumnoMateriasDTO> obtenerMateriasPorAlumno(String idAsignacion) {

        List<Object[]> rows = esta.obtenerMateriasYCalificaciones(idAsignacion);

        Map<String, List<MateriasCalificacionDTO>> mapa = new LinkedHashMap<>();
        Map<String, String> nombres = new HashMap<>();

        for (Object[] r : rows) {

            String idAlumno = (String) r[0];
            String nombre = (String) r[1];
            String nombreMateria = (String) r[2];
            BigDecimal calificacion = (BigDecimal) r[3];

            mapa.computeIfAbsent(idAlumno, x -> new ArrayList<>())
                .add(new MateriasCalificacionDTO(nombreMateria, calificacion));

            nombres.put(idAlumno, nombre);
        }

        List<AlumnoMateriasDTO> resultado = new ArrayList<>();

        for (String idAlumno : mapa.keySet()) {
            resultado.add(
                    new AlumnoMateriasDTO(
                            idAlumno,
                            nombres.get(idAlumno),
                            mapa.get(idAlumno)
                    )
            );
        }

        return resultado;
    }
    public List<AlumnoMateriasDTO> obtenerAlumnosConMateriasPorAsignacion(String idAsignacion) {

        // 1️⃣ Obtener alumnos inscritos en esa asignación
        List<InscritoAlumno> inscritos = inscritoAlumnoRepository.findByAsignacionId(idAsignacion);

        List<AlumnoMateriasDTO> resultado = new ArrayList<>();

        for (InscritoAlumno inscrito : inscritos) {

            String idAlumno = inscrito.getAlumno().getId();
            String nombreAlumno = inscrito.getAlumno().getNombre()+ " " +inscrito.getAlumno().getApellidoPaterno()+ " "+ inscrito.getAlumno().getApellidoMaterno();

            // 2️⃣ Obtener grado y ciclo de esa asignación (NO de otra)
            String idGrado = inscrito.getAsignacion().getGrado().getId();
            String idCiclo = inscrito.getAsignacion().getCiclo().getId();

            // 3️⃣ Traer calificaciones del alumno SOLO en esa asignación
            List<CalificacionFinalMateria> calificaciones =
                    calificacionRepository.obtenerCalificacionesAlumno(idAlumno, idGrado, idCiclo);

            // 4️⃣ Convertir a DTO de materias
            List<MateriasCalificacionDTO> materiasDTO = calificaciones.stream()
                    .map(c -> new MateriasCalificacionDTO(
                            c.getMateria().getNombre(),
                            c.getPromedio()
                    ))
                    .toList();

            // 5️⃣ Armar DTO final
            resultado.add(new AlumnoMateriasDTO(
                    idAlumno,
                    nombreAlumno,
                    materiasDTO
            ));
        }

        return resultado;
    }
}
