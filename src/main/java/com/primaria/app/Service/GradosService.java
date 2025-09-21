package com.primaria.app.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.GradoDTO;
import com.primaria.app.Model.Grado;
import com.primaria.app.repository.GradosRepository;
@Service
public class GradosService {

	 @Autowired
	    private GradosRepository gradoRepository;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public List<GradoDTO> listarTodos() {
	        return gradoRepository.findAll().stream()
	                .map(celular -> modelMapper.map(celular, GradoDTO.class))
	                .collect(Collectors.toList());
	    }
	    public Optional<GradoDTO> obtenerPorUuid(String uuid) {
	        return gradoRepository.findById(uuid)
	                .map(celular -> modelMapper.map(celular, GradoDTO.class));
	    }

	    public GradoDTO guardar(GradoDTO dto) {
	        Grado grupo = modelMapper.map(dto, Grado.class);
	       
	        grupo.setId(UUID.randomUUID().toString());
	        Grado guardado = gradoRepository.save(grupo);
	        return modelMapper.map(guardado, GradoDTO.class);
	    }

	    
	    public Grado save(Grado grado) {
	        return gradoRepository.save(grado);
	    }

	    
	    public boolean actualizar(String uuid, GradoDTO dto) {
	        Optional<Grado> existente = gradoRepository.findById(uuid);
	        if (existente.isPresent()) {
	            Grado grupo = existente.get();
	            grupo.setNombre(dto.getNombre());
	            grupo.setEstatus(dto.getEstatus());
	            gradoRepository.save(grupo);
	            return true;
	        }
	        return false;
	    }

	    public boolean eliminar(String uuid) {
	        Optional<Grado> existente = gradoRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	gradoRepository.delete(existente.get());
	            return true;
	        }
	        return false;
	    }
}
