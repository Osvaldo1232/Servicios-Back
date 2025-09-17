package com.primaria.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.primaria.app.Model.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, UUID> {
    // Puedes agregar consultas personalizadas si es necesario
}