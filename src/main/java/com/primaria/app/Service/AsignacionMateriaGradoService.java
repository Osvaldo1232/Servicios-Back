package com.primaria.app.Service;



import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.AsignacionMateriaGradoDTO;
import com.primaria.app.DTO.AsignacionMateriaGradoResumeDTO;
import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.Model.AsignacionMateriaGrado;
import com.primaria.app.Model.Grado;
import com.primaria.app.Model.Materia;
import com.primaria.app.repository.AsignacionMateriaGradoRepository;
import com.primaria.app.repository.GradosRepository;
import com.primaria.app.repository.MateriasRepository;


@Service
public class AsignacionMateriaGradoService {

    @Autowired
    private AsignacionMateriaGradoRepository asignacionMateriaGradoRepository;

    @Autowired
    private MateriasRepository materiaRepository;

    @Autowired
    private GradosRepository gradoRepository;

    public AsignacionMateriaGrado guardarAsignacion(AsignacionMateriaGradoDTO dto) {
        Materia materia = materiaRepository.findById(dto.getIdMateria()).orElse(null);
        Grado grado = gradoRepository.findById(dto.getIdGrado()).orElse(null);

        if (materia == null || grado == null) {
            throw new RuntimeException("Materia o grado no encontrados");
        }

        AsignacionMateriaGrado asignacion = new AsignacionMateriaGrado();
        asignacion.setMateria(materia);
        asignacion.setGrado(grado);

        return asignacionMateriaGradoRepository.save(asignacion);
    }
    
    
    public List<Map<String, String>> obtenerMateriasPorGrado(String idGrado) {
        List<AsignacionMateriaGrado> asignaciones = asignacionMateriaGradoRepository.findByGradoId(idGrado);

        return asignaciones.stream().map(a -> Map.of(
            "idMateria", a.getMateria().getId(),
            "nombreMateria", a.getMateria().getNombre()
        )).collect(Collectors.toList());
    }
    
    public List<AsignacionMateriaGradoResumeDTO> listarAsignacionesPorGrado(String idGrado) {
        return asignacionMateriaGradoRepository.findAll().stream()
                .filter(a -> a.getGrado() != null && a.getGrado().getId().equals(idGrado))
                .map(a -> {
                    String idMateria = a.getMateria() != null ? a.getMateria().getId() : "";
                    String nombreMateria = a.getMateria() != null ? a.getMateria().getNombre() : "";

                    String idCampoFormativo = (a.getMateria() != null && a.getMateria().getCampoFormativo() != null) 
                                              ? a.getMateria().getCampoFormativo().getId() : "";
                    String nombreCampoFormativo = (a.getMateria() != null && a.getMateria().getCampoFormativo() != null) 
                                                  ? a.getMateria().getCampoFormativo().getNombre() : "";

                    return new AsignacionMateriaGradoResumeDTO(
                            idGrado, a.getGrado().getNombre(),
                            idMateria, nombreMateria,
                            idCampoFormativo, nombreCampoFormativo
                    );
                }).toList();
    }
    
    public List<MateriaCalificacionResDTO> obtenerMateriasConPromedio(
            String idGrado, String idAlumno, String idCicloEscolar) {

        return asignacionMateriaGradoRepository.obtenerMateriasYPromedioPorGrado(idGrado, idAlumno, idCicloEscolar);
    }
}
