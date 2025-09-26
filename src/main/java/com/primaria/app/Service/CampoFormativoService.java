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

import com.primaria.app.DTO.CampoFormativoDTO;
import com.primaria.app.DTO.CampoFormativoResumenDTO;
import com.primaria.app.Model.CampoFormativo;
import com.primaria.app.Model.Estatus;
import com.primaria.app.repository.CampoFormativoRepository;







@Service
public class CampoFormativoService {

	 @Autowired
	    private CampoFormativoRepository campoFormativoRepository;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public Page<CampoFormativoDTO> listarTodos(Pageable pageable) {
	        return campoFormativoRepository.findAll(pageable)
	            .map(materia -> modelMapper.map(materia, CampoFormativoDTO.class));
	    }
	    
	   
	    public Optional<CampoFormativoDTO> obtenerPorUuid(String uuid) {
	        return campoFormativoRepository.findById(uuid)
	                .map(celular -> modelMapper.map(celular, CampoFormativoDTO.class));
	    }


	    public CampoFormativoDTO guardar(CampoFormativoDTO dto) {
	    	CampoFormativo grupo = modelMapper.map(dto, CampoFormativo.class);
	       
	        grupo.setId(UUID.randomUUID().toString());
	        CampoFormativo guardado = campoFormativoRepository.save(grupo);
	        return modelMapper.map(guardado, CampoFormativoDTO.class);
	    }

	    
	    public CampoFormativo save(CampoFormativo materia) {
	        return campoFormativoRepository.save(materia);
	    }

	    
	    public boolean actualizar(String uuid, CampoFormativoDTO dto) {
	        Optional<CampoFormativo> existente = campoFormativoRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	CampoFormativo ciclo = existente.get();
	          ciclo.setNombre(dto.getNombre());
	           ciclo.setEstatus(dto.getEstatus());
	           campoFormativoRepository.save(ciclo);
	            return true;
	        }
	        return false;
	    }

	    public boolean eliminar(String uuid) {
	        Optional<CampoFormativo> existente = campoFormativoRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	campoFormativoRepository.delete(existente.get());
	            return true;
	        }
	        return false;
	    }
	    
	    public List<CampoFormativoResumenDTO> obtenerActivos() {
	        List<CampoFormativo> activos = campoFormativoRepository.findByEstatus(Estatus.ACTIVO);
	        return activos.stream()
	                .map(campo -> new CampoFormativoResumenDTO(campo.getId(), campo.getNombre()))
	                .collect(Collectors.toList());
	    }
}
