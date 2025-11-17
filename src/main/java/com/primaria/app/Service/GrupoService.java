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
import com.primaria.app.exception.BusinessException;
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

	    
	    public Grupo save(Grupo grupo) {
	        List<Grupo> existentes = grupoRepository.findByNombre(grupo.getNombre().trim());

	        if (!existentes.isEmpty()) {
	            throw new IllegalArgumentException("Ya existe un grupo con el nombre: " + grupo.getNombre());
	        }

	        return grupoRepository.save(grupo);
	    }
	    
	    public boolean actualizar(String uuid, GrupoDTO dto) {

	        Optional<Grupo> existente = grupoRepository.findById(uuid);
	        if (existente.isEmpty()) {
	            throw new BusinessException(1002, "El grupo no existe");
	        }

	        List<Grupo> gruposConNombre = grupoRepository.findByNombre(dto.getNombre());

	        boolean nombreDuplicado = gruposConNombre.stream()
	                .anyMatch(g -> !g.getId().equals(uuid)); 

	        if (nombreDuplicado) {
	            throw new BusinessException(1001, "Ya existe un grupo con ese nombre");
	        }

	        Grupo grupo = existente.get();
	        grupo.setNombre(dto.getNombre());
	        grupo.setEstatus(dto.getEstatus());
	        grupoRepository.save(grupo);

	        return true;
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
