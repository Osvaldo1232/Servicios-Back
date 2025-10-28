package com.primaria.app.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
	    
	    
	    public List<TutorDTO> listarTodos() {
	        return tutorRepository.findAll()
	                .stream()
	                .map(tutor -> modelMapper.map(tutor, TutorDTO.class))
	                .toList();
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

	    
	    public Tutor save(Tutor tutor) {
	        return tutorRepository.save(tutor);
	    }

	    
	    public boolean actualizar(String uuid, TutorDTO dto) {
	        Optional<Tutor> existente = tutorRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	Tutor ciclo = existente.get();
	           ciclo.setNombre(dto.getNombre());
	           ciclo.setEstatus(dto.getEstatus());
	           ciclo.setApellidos(dto.getApellidos());
	           ciclo.setCorreo(dto.getCorreo());
	          
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
	    
	    public boolean cambiarEstatus(String uuid) {
	        try {
	            Optional<Tutor> optionalTutor = tutorRepository.findById(uuid);
	            if (optionalTutor.isPresent()) {
	                Tutor tutor = optionalTutor.get();
	                if (tutor.getEstatus() == Estatus.ACTIVO) {
	                    tutor.setEstatus(Estatus.INACTIVO);
	                } else {
	                    tutor.setEstatus(Estatus.ACTIVO);
	                }
	                tutorRepository.save(tutor);
	                return true;
	            }
	            return false;
	        } catch (IllegalArgumentException e) {
	            return false;
	        }
	    }

}
