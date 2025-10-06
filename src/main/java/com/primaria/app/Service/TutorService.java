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

import com.primaria.app.DTO.TutorDTO;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Tutor;
import com.primaria.app.repository.TutorRepository;
import com.primaria.app.DTO.TutorResumenDTO;


@Service
public class TutorService {

	 @Autowired
	    private TutorRepository tutorRepository;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public Page<TutorDTO> listarTodos(Pageable pageable) {
	        return tutorRepository.findAll(pageable)
	            .map(materia -> modelMapper.map(materia, TutorDTO.class));
	    }
	    
	   
	    public Optional<TutorDTO> obtenerPorUuid(String uuid) {
	        return tutorRepository.findById(uuid)
	                .map(celular -> modelMapper.map(celular, TutorDTO.class));
	    }


	    public TutorDTO guardar(TutorDTO dto) {
	    	Tutor grupo = modelMapper.map(dto, Tutor.class);
	       
	        grupo.setId(UUID.randomUUID().toString());
	        Tutor guardado = tutorRepository.save(grupo);
	        return modelMapper.map(guardado, TutorDTO.class);
	    }

	    
	    public Tutor save(Tutor materia) {
	        return tutorRepository.save(materia);
	    }

	    
	    public boolean actualizar(String uuid, TutorDTO dto) {
	        Optional<Tutor> existente = tutorRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	Tutor ciclo = existente.get();
	           ciclo.setNombre(dto.getNombre());
	           ciclo.setEstatus(dto.getEstatus());
	           ciclo.setApellidos(dto.getApellidos());
	           ciclo.setCorreo(dto.getCorreo());
	           ciclo.setParentesco(dto.getParentesco());
	           ciclo.setTelefono(dto.getTelefono());
	           
	           tutorRepository.save(ciclo);
	            return true;
	        }
	        return false;
	    }

	    public boolean eliminar(String uuid) {
	        Optional<Tutor> existente = tutorRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	tutorRepository.delete(existente.get());
	            return true;
	        }
	        return false;
	    }
	    
	    public List<TutorResumenDTO> obtenerActivos() {
	        List<Tutor> activos = tutorRepository.findByEstatus(Estatus.ACTIVO);
	        return activos.stream()
	                .map(campo -> new TutorResumenDTO(campo.getId(), campo.getNombre()))
	                .collect(Collectors.toList());
	    }
}
