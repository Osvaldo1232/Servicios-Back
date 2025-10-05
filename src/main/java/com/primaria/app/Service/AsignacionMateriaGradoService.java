package com.primaria.app.Service;



import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.AsignacionMateriaGradoDTO;
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
}
