package com.primaria.app.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.primaria.app.DTO.MateriaCampoDTO;
import com.primaria.app.DTO.MateriaDTO;
import com.primaria.app.Model.CampoFormativo;
import com.primaria.app.Model.Materia;
import com.primaria.app.exception.BusinessException;
import com.primaria.app.repository.CampoFormativoRepository;
import com.primaria.app.repository.MateriasRepository;








@Service
public class MateriasService {

	 @Autowired
	    private MateriasRepository materiasRepository;

	 
	 @Autowired
	 private CampoFormativoRepository campoFormativoRepository;
	    @Autowired
	    private ModelMapper modelMapper;
	    
	    
	    public List<MateriaDTO> listarTodos() {
	        return materiasRepository.findAll()
	                .stream()
	                .map(materia -> modelMapper.map(materia, MateriaDTO.class))
	                .toList();
	    }

	    public List<MateriaDTO> listarPorCampoFormativo(String idCampoFormativo) {
	        return materiasRepository.findByCampoFormativo_Id(idCampoFormativo)
	                .stream()
	                .map(m -> modelMapper.map(m, MateriaDTO.class))
	                .collect(Collectors.toList());
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

	      
	        if (materiasRepository.existsByNombreIgnoreCase(materia.getNombre())) {
	            throw new BusinessException(1000, "Ya existe una materia con ese nombre");
	        }

	        return materiasRepository.save(materia);
	    }
	        
	    
	    public boolean actualizar(String uuid, MateriaDTO dto) {

	        Materia materia = materiasRepository.findById(uuid)
	                .orElseThrow(() -> new BusinessException(1001, "Materia no encontrada"));

	        if (materiasRepository.existsByNombreIgnoreCaseAndIdNot(dto.getNombre(), uuid)) {
	            throw new BusinessException(1000, "Ya existe otra materia con ese nombre");
	        }

	        materia.setNombre(dto.getNombre());
	        materia.setEstatus(dto.getEstatus());

	        if (dto.getCampoFormativoId() != null) {
	            CampoFormativo campo = campoFormativoRepository.findById(dto.getCampoFormativoId())
	                    .orElseThrow(() -> new BusinessException(1002, "Campo formativo no encontrado"));
	            materia.setCampoFormativo(campo);
	        }

	        materiasRepository.save(materia);
	        return true;
	    }

	    public boolean eliminar(String uuid) {
	        Optional<Materia> existente = materiasRepository.findById(uuid);
	        if (existente.isPresent()) {
	        	materiasRepository.delete(existente.get());
	            return true;
	        }
	        return false;
	    }
	    
	    public List<MateriaCampoDTO> listarMateriasConCampoFormativo() {
	        return materiasRepository.findAll()
	                .stream()
	                .map(materia -> {
	                    MateriaCampoDTO dto = new MateriaCampoDTO();
	                    dto.setId(materia.getId());
	                 
	                    dto.setNombre(materia.getNombre());
	                    dto.setEstatus(materia.getEstatus());

	                    if (materia.getCampoFormativo() != null) {
	                        dto.setCampoFormativoId(materia.getCampoFormativo().getId().toString());
	                        dto.setCampoFormativoNombre(materia.getCampoFormativo().getNombre());
	                    }
	                    return dto;
	                })
	                .collect(Collectors.toList());
	    }
}
