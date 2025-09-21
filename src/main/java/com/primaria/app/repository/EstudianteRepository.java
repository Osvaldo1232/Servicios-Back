package com.primaria.app.repository;

import com.primaria.app.Model.Estudiante;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String> {
    // Puedes agregar consultas personalizadas si es necesario
	Optional<Estudiante> findByMatricula(String matricula);
	void deleteByMatricula(String matricula);
	Optional<Estudiante> findById(String id);
	
}