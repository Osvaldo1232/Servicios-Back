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

import com.primaria.app.DTO.TrimestresDTO;
import com.primaria.app.DTO.TrimestresResumenDTO;
import com.primaria.app.Model.Trimestres;
import com.primaria.app.Model.Estatus;
import com.primaria.app.repository.TrimestreRepository;







@Service
public class TrimestresService {

	 @Autowired
	    private TrimestreRepository trimestreRepository;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public List<TrimestresDTO> listarTodos() {
	        return trimestreRepository.findAll()
	                .stream()
	                .map(trimestre -> modelMapper.map(trimestre, TrimestresDTO.class))
	                .toList();
	    }

	   
	    public Optional<TrimestresDTO> obtenerPorUuid(String uuid) {
	        return trimestreRepository.findById(uuid)
	                .map(celular -> modelMapper.map(celular, TrimestresDTO.class));
	    }


	    public TrimestresDTO guardar(TrimestresDTO dto) {
	    	Trimestres grupo = modelMapper.map(dto, Trimestres.class);
	       
	        grupo.setId(UUID.randomUUID().toString());
	        Trimestres guardado = trimestreRepository.save(grupo);
	        return modelMapper.map(guardado, TrimestresDTO.class);
	    }

	    
	    public Trimestres save(Trimestres materia) {
	        return trimestreRepository.save(materia);
	    }

	    
	    public boolean actualizar(String uuid, TrimestresDTO dto) {
	        Optional<Trimestres> existente = trimestreRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	Trimestres ciclo = existente.get();
	          ciclo.setNombre(dto.getNombre());
	           ciclo.setEstatus(dto.getEstatus());
	           trimestreRepository.save(ciclo);
	            return true;
	        }
	        return false;
	    }

	    public boolean eliminar(String uuid) {
	        Optional<Trimestres> existente = trimestreRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	trimestreRepository.delete(existente.get());
	            return true;
	        }
	        return false;
	    }
	    
	    public List<TrimestresResumenDTO> obtenerActivos() {
	        List<Trimestres> activos = trimestreRepository.findByEstatus(Estatus.ACTIVO);
	        return activos.stream()
	                .map(campo -> new TrimestresResumenDTO(campo.getId(), campo.getNombre()))
	                .collect(Collectors.toList());
	    }
}
