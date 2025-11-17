package com.primaria.app.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.CicloEscolarDTO;
import com.primaria.app.DTO.CicloSimpleDTO;
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
        // Validar duplicados
        Optional<CicloEscolar> existente = CicloEscolaresRepository
            .findByAnioInicioAndAnioFin(dto.getAnioInicio(), dto.getAnioFin());

        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un ciclo escolar con esos años.");
        }

        // Guardar nuevo
        CicloEscolar nuevo = new CicloEscolar();
        nuevo.setAnioInicio(dto.getAnioInicio());
        nuevo.setAnioFin(dto.getAnioFin());
        nuevo.setEstatus(dto.getEstatus());


        CicloEscolar guardado = CicloEscolaresRepository.save(nuevo);

        return new CicloEscolarDTO(
        		guardado.getId(),
            guardado.getAnioInicio(),
            guardado.getAnioFin(),
            guardado.getEstatus(),
            guardado.getFechaCreado()
        );
    }
    
    public CicloEscolar save(CicloEscolar grado) {
        return CicloEscolaresRepository.save(grado);
    }

    
  

  
    
    public Optional<CicloEscolarDTO> obtenerCicloMasReciente() {
        CicloEscolar ciclo = CicloEscolaresRepository.findTopByOrderByFechaCreadoDesc();
        if (ciclo != null) {
            return Optional.of(modelMapper.map(ciclo, CicloEscolarDTO.class));
        }
        return Optional.empty();
    }
    public List<CicloSimpleDTO> obtenerCiclosFaltantesDeTutor(String idAlumno) {
        List<CicloEscolar> ciclos = CicloEscolaresRepository.obtenerCiclosSinTutorAsignado(idAlumno);

        return ciclos.stream()
                .map(c -> new CicloSimpleDTO(
                        c.getId(),
                        c.getAnioInicio() + "-"+c.getAnioFin()
                ))
                .collect(Collectors.toList());
    }

}
