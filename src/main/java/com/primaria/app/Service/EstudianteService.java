package com.primaria.app.Service;

import com.primaria.app.DTO.EstudianteDTO;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.repository.EstudianteRepository;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

	
	  @Autowired
	    private ModelMapper modelMapper;

	  
    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> findById(String id) {
        return estudianteRepository.findById(id);
    }

    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public void deleteById(String id) {
        estudianteRepository.deleteById(id);
    }
    
    public List<EstudianteDTO> listarTodos() {
        return estudianteRepository.findAll().stream()
                .map(alumno -> modelMapper.map(alumno, EstudianteDTO.class))
                .collect(Collectors.toList());
    }
  
    public Estudiante buscarPorMatricula(String matricula) {
        return estudianteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new RuntimeException("Estudiante con matrícula '" + matricula + "' no encontrado"));
    }
    
 
    
    public void eliminarPorMatricula(String matricula) {
        Optional<Estudiante> estudianteOpt = estudianteRepository.findByMatricula(matricula);
        if (estudianteOpt.isPresent()) {
            estudianteRepository.deleteByMatricula(matricula);
        } else {
            throw new RuntimeException("No se puede eliminar. Estudiante con matrícula '" + matricula + "' no encontrado");
        }
    }
    
    public void eliminarPorId(String id) {
        Optional<Estudiante> estudianteOpt = estudianteRepository.findById(id);
        if (estudianteOpt.isPresent()) {
            estudianteRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar. Estudiante con ID '" + id + "' no encontrado");
        }
    }
    
}