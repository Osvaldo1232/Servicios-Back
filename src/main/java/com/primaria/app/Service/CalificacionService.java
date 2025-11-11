package com.primaria.app.Service;

import com.primaria.app.DTO.*;
import com.primaria.app.Model.Calificacion_final;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.primaria.app.DTO.CalificacionFinalDTO;
import com.primaria.app.DTO.CicloCalificacionDTO;
import com.primaria.app.DTO.FiltroCalificacionesDTO;
import com.primaria.app.DTO.GradoCalificacionDTO;
import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.DTO.MateriaTrimestresDTO;
import com.primaria.app.Model.CicloEscolar;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.Grado;
import com.primaria.app.Model.Materia;
import com.primaria.app.Model.Trimestres;
import com.primaria.app.repository.CalificacionFinalRepository;
import com.primaria.app.repository.CicloEscolaresRepository;
import com.primaria.app.repository.EstudianteRepository;
import com.primaria.app.repository.GradosRepository;
import com.primaria.app.repository.InscritoAlumnoRepository;
import com.primaria.app.repository.MateriasRepository;
import com.primaria.app.repository.TrimestreRepository;

import jakarta.transaction.Transactional;

import com.primaria.app.DTO.AlumnoCalificacionesDTO;
import com.primaria.app.DTO.CalificacionAlumnoCicloDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private InscritoAlumnoRepository inscritoRepo;
    @Autowired
    private GradosRepository gradoRepo;

    public Calificacion_final asignarCalificaciosn(CalificacionFinalDTO dto) {
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

        // Validar duplicado
        Optional<Calificacion_final> existente = calificacionRepo
                .findByAlumnoIdAndMateriaIdAndTrimestreIdAndCicloId(
                        alumno.getId(), materia.getId(), trimestre.getId(), ciclo.getId()
                );
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe una calificación para este alumno, materia, trimestre y ciclo");
        }

        Calificacion_final calificacion = new Calificacion_final();
        calificacion.setAlumno(alumno);
        calificacion.setMateria(materia);
        calificacion.setTrimestre(trimestre);
        calificacion.setCiclo(ciclo);
        calificacion.setGrado(grado);
        calificacion.setPromedio(dto.getPromedio());

        return calificacionRepo.save(calificacion);
    }
    
    public Calificacion_final asignarCalificacion(CalificacionFinalDTO dto) {
        Calificacion_final calificacion;

        if (dto.getId() != null && !dto.getId().isEmpty()) {
            // Actualización
            calificacion = calificacionRepo.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Calificación no encontrada"));
        } else {
            // Creación: validar duplicado
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

        // Siempre asignar el promedio (ya sea nueva o actualización)
        calificacion.setPromedio(dto.getPromedio());

        return calificacionRepo.save(calificacion);
    }

    @Transactional
    public void asignarCalificaciones(List<CalificacionFinalDTO> calificaciones) {
        for (CalificacionFinalDTO dto : calificaciones) {
            asignarCalificacion(dto);
        }
    }

    public Optional<Calificacion_final> buscarPorId(String id) {
        return calificacionRepo.findById(id);
    }

    public List<AlumnoCalificacionesDTO> listarCalificacionesPorGrupo(FiltroCalificacionesDTO filtro) {
        List<Estudiante> alumnos = inscritoRepo.findAlumnosPorCicloGradoGrupo(
            filtro.getIdCiclo(), filtro.getIdGrado(), filtro.getIdGrupo()
        );
        Map<String, AlumnoCalificacionesDTO> mapAlumnos = new HashMap<>();
        for (Estudiante alumno : alumnos) {
            mapAlumnos.put(alumno.getId(),
                new AlumnoCalificacionesDTO(alumno.getId(), alumno.getNombre(), new HashMap<>()));
        }
        List<Calificacion_final> calificaciones = calificacionRepo.findByCicloIdAndAlumnoIn(filtro.getIdCiclo(), alumnos);
        for (Calificacion_final c : calificaciones) {
            AlumnoCalificacionesDTO dto = mapAlumnos.get(c.getAlumno().getId());
            dto.getCalificacionesPorTrimestre().put(c.getTrimestre().getNombre(), c.getPromedio());
        }
        return new ArrayList<>(mapAlumnos.values());
    }

    public List<MateriaTrimestresDTO> obtenerCalificacionesPorAlumnoYCiclo(String alumnoId, String cicloId) {
        List<Calificacion_final> calificaciones = calificacionRepo.findByAlumno_IdAndCiclo_Id(alumnoId, cicloId);
        Map<String, List<Calificacion_final>> porMateria = calificaciones.stream()
                .collect(Collectors.groupingBy(c -> c.getMateria().getId()));
        List<MateriaTrimestresDTO> resultado = new ArrayList<>();
        for (List<Calificacion_final> listaMateria : porMateria.values()) {
            Calificacion_final ejemplo = listaMateria.get(0);
            String nombreAlumno = ejemplo.getAlumno().getNombre();
            String nombreMateria = ejemplo.getMateria().getNombre();
            MateriaTrimestresDTO dto = new MateriaTrimestresDTO(nombreAlumno, nombreMateria);
            listaMateria.sort(Comparator.comparing(c -> c.getTrimestre().getNombre()));
            int contador = 1;
            for (Calificacion_final c : listaMateria) {
                dto.agregarCalificacion("calificacionTrimestral" + contador, c.getPromedio());
                contador++;
            }
            resultado.add(dto);
        }
        return resultado;
    }
    
    // -------------------------------
    // Método que faltaba
    // -------------------------------
    public List<MateriaCalificacionResDTO> obtenerCalificacionesPorAlumnoYciclo(String alumnoId, String cicloId) {
        // Obtener todas las calificaciones del alumno en el ciclo
        List<Calificacion_final> calificaciones = calificacionRepo.findByAlumno_IdAndCiclo_Id(alumnoId, cicloId);

        // Agrupar calificaciones por materia
        Map<String, List<Calificacion_final>> porMateria = calificaciones.stream()
                .collect(Collectors.groupingBy(c -> c.getMateria().getId()));

        List<MateriaCalificacionResDTO> resultado = new ArrayList<>();

        for (List<Calificacion_final> calList : porMateria.values()) {
            Calificacion_final ejemplo = calList.get(0);

            String idMateria = ejemplo.getMateria().getId();
            String nombreMateria = ejemplo.getMateria().getNombre();
            String idGrado = ejemplo.getGrado() != null ? ejemplo.getGrado().getId() : "";
            String nombreGrado = ejemplo.getGrado() != null ? ejemplo.getGrado().getNombre() : "";

            // Sumar los promedios de los trimestres usando BigDecimal
            BigDecimal suma = calList.stream()
                    .map(c -> BigDecimal.valueOf(c.getPromedio()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal promedio = suma.divide(BigDecimal.valueOf(calList.size()), 2, RoundingMode.HALF_UP);

            // Crear DTO
            resultado.add(new MateriaCalificacionResDTO(
                    idMateria,
                    nombreMateria,
                    promedio,
                    idGrado,
                    nombreGrado
            ));
        }

        return resultado;
    }
    



   public List<CicloCalificacionDTO> obtenerCalificacionesPorAlumno(String idAlumno) {
    List<Calificacion_final> calificaciones = calificacionRepo.findAllByAlumnoOrdenado(idAlumno);

    Map<String, List<Calificacion_final>> porCiclo = calificaciones.stream()
            .collect(Collectors.groupingBy(
                    cf -> cf.getCiclo().getAnioInicio() + "-" + cf.getCiclo().getAnioFin(),
                    LinkedHashMap::new,
                    Collectors.toList()
            ));

    List<CicloCalificacionDTO> resultado = new ArrayList<>();

    for (Map.Entry<String, List<Calificacion_final>> entryCiclo : porCiclo.entrySet()) {
        String cicloNombre = entryCiclo.getKey();
        List<Calificacion_final> calificacionesCiclo = entryCiclo.getValue();

        Map<String, List<Calificacion_final>> porGrado = calificacionesCiclo.stream()
                .collect(Collectors.groupingBy(cf -> cf.getGrado().getNombre(), LinkedHashMap::new, Collectors.toList()));

        List<GradoCalificacionDTO> gradosDTO = new ArrayList<>();

        for (Map.Entry<String, List<Calificacion_final>> entryGrado : porGrado.entrySet()) {
            String gradoNombre = entryGrado.getKey();
            List<Calificacion_final> calificacionesGrado = entryGrado.getValue();

            String alumnoId = calificacionesGrado.get(0).getAlumno().getId();
            String nombreAlumno = calificacionesGrado.get(0).getAlumno().getNombre() + " "
                    + calificacionesGrado.get(0).getAlumno().getApellidoPaterno() + " "
                    + calificacionesGrado.get(0).getAlumno().getApellidoMaterno();

            Map<String, List<Calificacion_final>> porMateria = calificacionesGrado.stream()
                    .collect(Collectors.groupingBy(cf -> cf.getMateria().getNombre(), LinkedHashMap::new, Collectors.toList()));

            List<MateriaCalificacionPDFDTO> materiasDTO = new ArrayList<>();

            for (Map.Entry<String, List<Calificacion_final>> entryMateria : porMateria.entrySet()) {
                String materiaNombre = entryMateria.getKey();
                List<Calificacion_final> calificacionesMateria = entryMateria.getValue();

                Double tri1 = calificacionesMateria.size() > 0 ? calificacionesMateria.get(0).getPromedio() : 0.0;
                Double tri2 = calificacionesMateria.size() > 1 ? calificacionesMateria.get(1).getPromedio() : 0.0;
                Double tri3 = calificacionesMateria.size() > 2 ? calificacionesMateria.get(2).getPromedio() : 0.0;

                Double promedioFinal = (tri1 + tri2 + tri3) / 3.0;

                materiasDTO.add(new MateriaCalificacionPDFDTO(materiaNombre, tri1, tri2, tri3, promedioFinal));
            }

            Double promedioGrado = materiasDTO.stream()
                    .mapToDouble(MateriaCalificacionPDFDTO::getCalificacionFinal)
                    .average()
                    .orElse(0.0);

            gradosDTO.add(new GradoCalificacionDTO(gradoNombre, alumnoId, nombreAlumno, materiasDTO, promedioGrado));
        }

        resultado.add(new CicloCalificacionDTO(cicloNombre, gradosDTO));
    }

    return resultado;
}
   public List<CicloCalificacionDTO> obtenerCalificacionesPorAlumnos(String idAlumno) {
	    List<Calificacion_final> calificaciones = calificacionRepo.findAllByAlumnoOrdenado(idAlumno);

	    Map<String, List<Calificacion_final>> porCiclo = calificaciones.stream()
	            .collect(Collectors.groupingBy(
	                    cf -> cf.getCiclo().getAnioInicio() + "-" + cf.getCiclo().getAnioFin(),
	                    LinkedHashMap::new,
	                    Collectors.toList()
	            ));

	    List<CicloCalificacionDTO> resultado = new ArrayList<>();

	    for (Map.Entry<String, List<Calificacion_final>> entryCiclo : porCiclo.entrySet()) {
	        String cicloNombre = entryCiclo.getKey();
	        List<Calificacion_final> calificacionesCiclo = entryCiclo.getValue();

	        Map<String, List<Calificacion_final>> porGrado = calificacionesCiclo.stream()
	                .collect(Collectors.groupingBy(cf -> cf.getGrado().getNombre(), LinkedHashMap::new, Collectors.toList()));

	        List<GradoCalificacionDTO> gradosDTO = new ArrayList<>();
	        boolean cicloValido = true; // bandera para validar todo el ciclo

	        for (Map.Entry<String, List<Calificacion_final>> entryGrado : porGrado.entrySet()) {
	            String gradoNombre = entryGrado.getKey();
	            List<Calificacion_final> calificacionesGrado = entryGrado.getValue();

	            String alumnoId = calificacionesGrado.get(0).getAlumno().getId();
	            String nombreAlumno = calificacionesGrado.get(0).getAlumno().getNombre() + " "
	                    + calificacionesGrado.get(0).getAlumno().getApellidoPaterno() + " "
	                    + calificacionesGrado.get(0).getAlumno().getApellidoMaterno();

	            Map<String, List<Calificacion_final>> porMateria = calificacionesGrado.stream()
	                    .collect(Collectors.groupingBy(cf -> cf.getMateria().getNombre(), LinkedHashMap::new, Collectors.toList()));

	            List<MateriaCalificacionPDFDTO> materiasDTO = new ArrayList<>();

	            for (Map.Entry<String, List<Calificacion_final>> entryMateria : porMateria.entrySet()) {
	                List<Calificacion_final> calificacionesMateria = entryMateria.getValue();

	                // Si alguna materia no tiene las 3 calificaciones, el ciclo no es válido
	                if (calificacionesMateria.size() < 3) {
	                    cicloValido = false;
	                    break;
	                }

	                Double tri1 = calificacionesMateria.get(0).getPromedio();
	                Double tri2 = calificacionesMateria.get(1).getPromedio();
	                Double tri3 = calificacionesMateria.get(2).getPromedio();

	                Double promedioFinal = (tri1 + tri2 + tri3) / 3.0;
	                promedioFinal = Math.round(promedioFinal * 100.0) / 100.0;

	                materiasDTO.add(new MateriaCalificacionPDFDTO(entryMateria.getKey(), tri1, tri2, tri3, promedioFinal));
	            }

	            // Si el ciclo ya es inválido, no agregamos más grados
	            if (!cicloValido) {
	                break;
	            }

	            Double promedioGrado = materiasDTO.stream()
	                    .mapToDouble(MateriaCalificacionPDFDTO::getCalificacionFinal)
	                    .average()
	                    .orElse(0.0);
	            promedioGrado = Math.round(promedioGrado * 100.0) / 100.0;

	            gradosDTO.add(new GradoCalificacionDTO(gradoNombre, alumnoId, nombreAlumno, materiasDTO, promedioGrado));
	        }

	        // Solo agregamos el ciclo si es válido (todas las materias tienen 3 calificaciones)
	        if (cicloValido) {
	            resultado.add(new CicloCalificacionDTO(cicloNombre, gradosDTO));
	        }
	    }

	    return resultado;
	}



}
