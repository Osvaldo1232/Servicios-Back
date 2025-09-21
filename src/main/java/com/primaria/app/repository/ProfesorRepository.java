package com.primaria.app.repository;

import com.primaria.app.Model.Profesor;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, String> {
    // Puedes agregar consultas personalizadas si es necesario
}