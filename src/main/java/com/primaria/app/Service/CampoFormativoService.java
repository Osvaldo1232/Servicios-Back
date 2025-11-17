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
import com.primaria.app.exception.BusinessException;
import com.primaria.app.repository.CampoFormativoRepository;







@Service
public class CampoFormativoService {

	 @Autowired
	    private CampoFormativoRepository campoFormativoRepository;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public List<CampoFormativoDTO> listarTodos() {
	        return campoFormativoRepository.findAll()
	                .stream()
	                .map(campo -> modelMapper.map(campo, CampoFormativoDTO.class))
	                .toList();
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

	    
	    public CampoFormativo save(CampoFormativo campo) {

	        List<CampoFormativo> existentes = campoFormativoRepository.findByNombre(campo.getNombre());

	        if (!existentes.isEmpty()) {
	            throw new BusinessException(2000, "Ya existe un campo formativo con ese nombre.");
	        }

	        return campoFormativoRepository.save(campo);
	    }

	    
	    public boolean actualizar(String uuid, CampoFormativoDTO dto) {

	        Optional<CampoFormativo> existente = campoFormativoRepository.findById(uuid);
	        if (existente.isEmpty()) {
	            throw new BusinessException(2002, "El campo formativo no existe.");
	        }
	        CampoFormativo actual = existente.get();
	        List<CampoFormativo> mismosNombres = campoFormativoRepository.findByNombre(dto.getNombre());
	        boolean nombreDuplicado = mismosNombres.stream()
	                .anyMatch(c -> !c.getId().equals(uuid)); 
	        if (nombreDuplicado) {
	            throw new BusinessException(2001, "Ya existe un campo formativo con ese nombre.");
	        }
	        actual.setNombre(dto.getNombre());
	        actual.setEstatus(dto.getEstatus());
	        campoFormativoRepository.save(actual);
	        return true;
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
