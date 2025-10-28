package com.primaria.app.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.primaria.app.DTO.MateriaDTO;
import com.primaria.app.Model.Materia;
import com.primaria.app.repository.MateriasRepository;








@Service
public class MateriasService {

	 @Autowired
	    private MateriasRepository materiasRepository;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public List<MateriaDTO> listarTodos() {
	        return materiasRepository.findAll()
	                .stream()
	                .map(materia -> modelMapper.map(materia, MateriaDTO.class))
	                .toList();
	    }

	    public Optional<MateriaDTO> obtenerPorUuid(String uuid) {
	        return materiasRepository.findById(uuid)
	                .map(celular -> modelMapper.map(celular, MateriaDTO.class));
	    }

	    public MateriaDTO guardar(MateriaDTO dto) {
	    	Materia grupo = modelMapper.map(dto, Materia.class);
	       
	        grupo.setId(UUID.randomUUID().toString());
	        Materia guardado = materiasRepository.save(grupo);
	        return modelMapper.map(guardado, MateriaDTO.class);
	    }

	    
	    public Materia save(Materia materia) {
	        return materiasRepository.save(materia);
	    }

	    
	    public boolean actualizar(String uuid, MateriaDTO dto) {
	        Optional<Materia> existente = materiasRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	Materia materia = existente.get();
	        	materia.setNombre(dto.getNombre());
	        	materia.setEstatus(dto.getEstatus());
	           materiasRepository.save(materia);
	            return true;
	        }
	        return false;
	    }

	    public boolean eliminar(String uuid) {
	        Optional<Materia> existente = materiasRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	materiasRepository.delete(existente.get());
	            return true;
	        }
	        return false;
	    }
}
