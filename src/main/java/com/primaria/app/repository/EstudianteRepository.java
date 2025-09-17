package com.primaria.app.repository;

import com.primaria.app.Model.Estudiante;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, UUID> {
    // Puedes agregar consultas personalizadas si es necesario
	
	
}