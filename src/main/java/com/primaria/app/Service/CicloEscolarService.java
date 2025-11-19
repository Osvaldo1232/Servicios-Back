package com.primaria.app.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.AsignacionSelectDTO;
import com.primaria.app.DTO.CicloEscolarDTO;
import com.primaria.app.DTO.CicloSimpleDTO;
import com.primaria.app.Model.CicloEscolar;
import com.primaria.app.Model.Estatus;
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

        // Crear nuevo ciclo
        CicloEscolar nuevo = new CicloEscolar();
        nuevo.setAnioInicio(dto.getAnioInicio());
        nuevo.setAnioFin(dto.getAnioFin());

        // 🚀 Estatus automático
        Estatus estatusCalculado = calcularEstatusAutomatico(dto.getAnioInicio(), dto.getAnioFin());
        nuevo.setEstatus(estatusCalculado);

        // 🚨 Si este ciclo será ACTIVO, desactivar el actual activo
        if (estatusCalculado == Estatus.ACTIVO) {
            desactivarCicloActivoAnterior();
        }

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
    private Estatus calcularEstatusAutomatico(int anioInicio, int anioFin) {
        LocalDate hoy = LocalDate.now();

        LocalDate inicio = LocalDate.of(anioInicio, 9, 1); // 1 septiembre del añoInicio
        LocalDate fin = LocalDate.of(anioFin, 7, 31);      // 31 julio del añoFin

        // Si la fecha actual está entre inicio y fin
        if (!hoy.isBefore(inicio) && !hoy.isAfter(fin)) {
            return Estatus.ACTIVO;
        }

        return Estatus.INACTIVO;
    }
    private void desactivarCicloActivoAnterior() {
        Optional<CicloEscolar> activo = CicloEscolaresRepository.findByEstatus(Estatus.ACTIVO);
        if (activo.isPresent()) {
            CicloEscolar ciclo = activo.get();
            ciclo.setEstatus(Estatus.INACTIVO);
            CicloEscolaresRepository.save(ciclo);
        }
    }

    public List<AsignacionSelectDTO> obtenerCiclosActivos() {
        Optional<CicloEscolar> activos = CicloEscolaresRepository.findByEstatus(Estatus.ACTIVO);

        return activos.stream()
                .map(ciclo -> new AsignacionSelectDTO(
                        ciclo.getId(),
                        ciclo.getAnioInicio() + "-" + ciclo.getAnioFin()
                ))
                .toList();
    }
}
