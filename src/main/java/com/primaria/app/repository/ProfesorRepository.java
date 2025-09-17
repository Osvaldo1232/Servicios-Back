package com.primaria.app.repository;

import com.primaria.app.Model.Profesor;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, UUID> {
    // Puedes agregar consultas personalizadas si es necesario
}