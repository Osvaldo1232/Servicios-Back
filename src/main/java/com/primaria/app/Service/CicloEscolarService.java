package com.primaria.app.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.CicloEscolarDTO;

import com.primaria.app.Model.CicloEscolar;

import com.primaria.app.repository.CicloEscolaresRepository;







@Service
public class CicloEscolarService {

	@Autowired
    private CicloEscolaresRepository CicloEscolaresRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    
    public List<CicloEscolarDTO> listarTodos() {
        return CicloEscolaresRepository.findAll().stream()
                .map(celular -> modelMapper.map(celular, CicloEscolarDTO.class))
                .collect(Collectors.toList());
    }
    public Optional<CicloEscolarDTO> obtenerPorUuid(String uuid) {
        return CicloEscolaresRepository.findById(uuid)
                .map(celular -> modelMapper.map(celular, CicloEscolarDTO.class));
    }

    public CicloEscolarDTO guardar(CicloEscolarDTO dto) {
        CicloEscolar grupo = modelMapper.map(dto, CicloEscolar.class);
       
        grupo.setId(UUID.randomUUID().toString());
        CicloEscolar guardado = CicloEscolaresRepository.save(grupo);
        return modelMapper.map(guardado, CicloEscolarDTO.class);
    }

    
    public CicloEscolar save(CicloEscolar grado) {
        return CicloEscolaresRepository.save(grado);
    }

    
    public boolean actualizar(String uuid, CicloEscolarDTO dto) {
        Optional<CicloEscolar> existente = CicloEscolaresRepository.findById(uuid);
        if (existente.isPresent()) {
        	CicloEscolar ciclo = existente.get();
        	ciclo.setFechaInicio(dto.getFechaInicio());
        	ciclo.setFechaFin(dto.getFechaFin());
        	ciclo.setEstatus(dto.getEstatus());
           
            CicloEscolaresRepository.save(ciclo);
            return true;
        }
        return false;
    }

    public boolean eliminar(String uuid) {
        Optional<CicloEscolar> existente = CicloEscolaresRepository.findById(uuid);
        if (existente.isPresent()) {
        	CicloEscolaresRepository.delete(existente.get());
            return true;
        }
        return false;
    }
    
    public Optional<CicloEscolarDTO> obtenerCicloMasReciente() {
        CicloEscolar ciclo = CicloEscolaresRepository.findTopByOrderByFechaCreadoDesc();
        if (ciclo != null) {
            return Optional.of(modelMapper.map(ciclo, CicloEscolarDTO.class));
        }
        return Optional.empty();
    }

}
