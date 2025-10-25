package com.primaria.app.Service;



import com.primaria.app.DTO.CalificacionFinalDTO;
import com.primaria.app.DTO.FiltroCalificacionesDTO;
import com.primaria.app.Model.Calificacion_final;
import com.primaria.app.Model.CicloEscolar;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.Materia;
import com.primaria.app.Model.Trimestres;
import com.primaria.app.repository.CalificacionFinalRepository;
import com.primaria.app.repository.CicloEscolaresRepository;
import com.primaria.app.repository.EstudianteRepository;
import com.primaria.app.repository.InscritoAlumnoRepository;
import com.primaria.app.repository.MateriasRepository;
import com.primaria.app.repository.TrimestreRepository;
import com.primaria.app.DTO.AlumnoCalificacionesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionFinalRepository calificacionRepo;

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private MateriasRepository materiaRepo;

    @Autowired
    private TrimestreRepository trimestreRepo;

    @Autowired
    private CicloEscolaresRepository cicloRepo;
    @Autowired
    private  InscritoAlumnoRepository inscritoRepo;
   
    

    public Calificacion_final asignarCalificacion(CalificacionFinalDTO dto) {

        // Traer entidades desde la base de datos
        Estudiante alumno = estudianteRepo.findById(dto.getIdAlumno())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Materia materia = materiaRepo.findById(dto.getIdMateria())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        Trimestres trimestre = trimestreRepo.findById(dto.getIdTrimestre())
                .orElseThrow(() -> new RuntimeException("Trimestre no encontrado"));

        CicloEscolar ciclo = cicloRepo.findById(dto.getIdCicloEscolar())
                .orElseThrow(() -> new RuntimeException("Ciclo escolar no encontrado"));

        // Crear la calificación
        Calificacion_final calificacion = new Calificacion_final();
        calificacion.setAlumno(alumno);
        calificacion.setMateria(materia);
        calificacion.setTrimestre(trimestre);
        calificacion.setCiclo(ciclo);
        calificacion.setPromedio(dto.getPromedio());

        // Validar duplicado
        Optional<Calificacion_final> existente = calificacionRepo
                .findByAlumnoIdAndMateriaIdAndTrimestreIdAndCicloId(
                        alumno.getId(),
                        materia.getId(),
                        trimestre.getId(),
                        ciclo.getId()
                );

        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe una calificación para este alumno, materia, trimestre y ciclo");
        }

        return calificacionRepo.save(calificacion);
    }


    public Optional<Calificacion_final> buscarPorId(String id) {
        return calificacionRepo.findById(id);
    }
    
    
    public List<AlumnoCalificacionesDTO> listarCalificacionesPorGrupo(FiltroCalificacionesDTO filtro) {
        // 1️⃣ Obtener alumnos inscritos
        List<Estudiante> alumnos = inscritoRepo.findAlumnosPorCicloGradoGrupo(
            filtro.getIdCiclo(), filtro.getIdGrado(), filtro.getIdGrupo()
        );

        Map<String, AlumnoCalificacionesDTO> mapAlumnos = new HashMap<>();
        for (Estudiante alumno : alumnos) {
            mapAlumnos.put(alumno.getId(),
                new AlumnoCalificacionesDTO(alumno.getId(), alumno.getNombre(), new HashMap<>()));
        }

        // 2️⃣ Obtener calificaciones
        List<Calificacion_final> calificaciones = calificacionRepo.findByCicloIdAndAlumnoIn(filtro.getIdCiclo(), alumnos);
        for (Calificacion_final c : calificaciones) {
            AlumnoCalificacionesDTO dto = mapAlumnos.get(c.getAlumno().getId());
            dto.getCalificacionesPorTrimestre().put(c.getTrimestre().getNombre(), c.getPromedio());
        }

        return new ArrayList<>(mapAlumnos.values());
    }


}
