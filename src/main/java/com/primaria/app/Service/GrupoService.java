package com.primaria.app.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.GrupoDTO;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.Grupo;
import com.primaria.app.repository.GrupoRepository;





@Service
public class GrupoService {

	 @Autowired
	    private GrupoRepository grupoRepository;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public List<GrupoDTO> listarTodos() {
	        return grupoRepository.findAll().stream()
	                .map(celular -> modelMapper.map(celular, GrupoDTO.class))
	                .collect(Collectors.toList());
	    }
	    public Optional<GrupoDTO> obtenerPorUuid(String uuid) {
	        return grupoRepository.findById(uuid)
	                .map(celular -> modelMapper.map(celular, GrupoDTO.class));
	    }

	    public GrupoDTO guardar(GrupoDTO dto) {
	        Grupo grupo = modelMapper.map(dto, Grupo.class);
	       
	        grupo.setId(UUID.randomUUID().toString());
	        Grupo guardado = grupoRepository.save(grupo);
	        return modelMapper.map(guardado, GrupoDTO.class);
	    }

	    
	    public Grupo save(Grupo estudiante) {
	        return grupoRepository.save(estudiante);
	    }

	    
	    public boolean actualizar(String uuid, GrupoDTO dto) {
	        Optional<Grupo> existente = grupoRepository.findById(uuid);
	        if (existente.isPresent()) {
	            Grupo grupo = existente.get();
	            grupo.setNombre(dto.getNombre());
	            grupo.setEstatus(dto.getEstatus());
	            grupoRepository.save(grupo);
	            return true;
	        }
	        return false;
	    }

	    public boolean eliminar(String uuid) {
	        Optional<Grupo> existente = grupoRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	grupoRepository.delete(existente.get());
	            return true;
	        }
	        return false;
	    }
}
