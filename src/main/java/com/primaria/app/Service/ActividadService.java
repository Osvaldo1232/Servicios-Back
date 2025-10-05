package com.primaria.app.Service;



import com.primaria.app.DTO.ActividadDTO;
import com.primaria.app.Model.*;
import com.primaria.app.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActividadService {

    @Autowired
    private actividadRepository actividadRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

 

    @Autowired
    private CicloEscolaresRepository cicloRepository;

    @Autowired
    private MateriasRepository materiaRepository;

    @Autowired
    private GradosRepository gradoRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private TrimestreRepository trimestreRepository;

    @Autowired
    private TipoEvaluacionRepository tipoEvaluacionRepository;

    // Guardar actividad
    public String guardarActividad(ActividadDTO dto) {
        Profesor docente = profesorRepository.findById(dto.getIdDocente()).orElse(null);
      
        CicloEscolar ciclo = cicloRepository.findById(dto.getIdCiclo()).orElse(null);
        Materia materia = materiaRepository.findById(dto.getIdMateria()).orElse(null);
        Grado grado = gradoRepository.findById(dto.getIdGrado()).orElse(null);
        Grupo grupo = grupoRepository.findById(dto.getIdGrupo()).orElse(null);
        Trimestres trimestre = trimestreRepository.findById(dto.getIdTrimestre()).orElse(null);
        Tipos_Evaluacion tipoEvaluacion = tipoEvaluacionRepository.findById(dto.getIdTipoEvaluacion()).orElse(null);

        if(docente == null  || ciclo == null || materia == null || grado == null || grupo == null
            || trimestre == null || tipoEvaluacion == null) {
            throw new RuntimeException("Alguna de las referencias no existe");
        }

        Actividad actividad = new Actividad();
        actividad.setDocente(docente);
        
        actividad.setCiclo(ciclo);
        actividad.setMateria(materia);
        actividad.setGrado(grado);
        actividad.setGrupo(grupo);
        actividad.setTrimestre(trimestre);
        actividad.setTipoEvaluacion(tipoEvaluacion);
        actividad.setNombre(dto.getNombre());
        actividad.setFecha(dto.getFecha());
        actividad.setValor(dto.getValor());

        Actividad saved = actividadRepository.save(actividad);
        return saved.getId(); // Solo devolvemos el ID
    }

   
    public List<Actividad> filtrarActividades(String docenteId,  String gradoId, String grupoId, String materiaId, String cicloId) {
       
        List<Actividad> resultado = actividadRepository.filtrar(docenteId, gradoId, grupoId, materiaId, cicloId);

        if(resultado.isEmpty()) {
            throw new RuntimeException("No se encontraron actividades con los filtros proporcionados");
        }
        return resultado;
    }
}
