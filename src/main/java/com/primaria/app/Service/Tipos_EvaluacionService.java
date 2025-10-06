package com.primaria.app.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.Tipos_EvaluacionDTO;
import com.primaria.app.DTO.Tipos_EvaluacionResumenDTO;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Tipos_Evaluacion;
import com.primaria.app.repository.Tipos_EvaluacionRepository;


@Service
public class Tipos_EvaluacionService {

	 @Autowired
	    private Tipos_EvaluacionRepository tipos_EvaluacionRepository;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public Page<Tipos_EvaluacionDTO> listarTodos(Pageable pageable) {
	        return tipos_EvaluacionRepository.findAll(pageable)
	            .map(materia -> modelMapper.map(materia, Tipos_EvaluacionDTO.class));
	    }
	    
	   
	    public Optional<Tipos_EvaluacionDTO> obtenerPorUuid(String uuid) {
	        return tipos_EvaluacionRepository.findById(uuid)
	                .map(celular -> modelMapper.map(celular, Tipos_EvaluacionDTO.class));
	    }


	    public Tipos_EvaluacionDTO guardar(Tipos_EvaluacionDTO dto) {
	    	Tipos_Evaluacion grupo = modelMapper.map(dto, Tipos_Evaluacion.class);
	       
	        grupo.setId(UUID.randomUUID().toString());
	        Tipos_Evaluacion guardado = tipos_EvaluacionRepository.save(grupo);
	        return modelMapper.map(guardado, Tipos_EvaluacionDTO.class);
	    }

	    
	    public Tipos_Evaluacion save(Tipos_Evaluacion tipo_evaluacion) {
	        return tipos_EvaluacionRepository.save(tipo_evaluacion);
	    }

	    
	    public boolean actualizar(String uuid, Tipos_EvaluacionDTO tipos_EvaluacionDTO) {
	        Optional<Tipos_Evaluacion> existente = tipos_EvaluacionRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	Tipos_Evaluacion tipos_evaluacion = existente.get();
	        	tipos_evaluacion.setNombre(tipos_EvaluacionDTO.getNombre());
	        	tipos_evaluacion.setEstatus(tipos_EvaluacionDTO.getEstatus());
	           
	        	tipos_EvaluacionRepository.save(tipos_evaluacion);
	            return true;
	        }
	        return false;
	    }

	    public boolean eliminar(String uuid) {
	        Optional<Tipos_Evaluacion> existente = tipos_EvaluacionRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	tipos_EvaluacionRepository.delete(existente.get());
	            return true;
	        }
	        return false;
	    }
	    
	    public List<Tipos_EvaluacionResumenDTO> obtenerActivos() {
	        List<Tipos_Evaluacion> activos = tipos_EvaluacionRepository.findByEstatus(Estatus.ACTIVO);
	        return activos.stream()
	                .map(campo -> new Tipos_EvaluacionResumenDTO(campo.getId(), campo.getNombre()))
	                .collect(Collectors.toList());
	    }
}
