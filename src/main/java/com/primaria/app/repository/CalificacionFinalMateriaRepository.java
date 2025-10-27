package com.primaria.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.primaria.app.Model.CalificacionFinalMateria;

@Repository
public interface CalificacionFinalMateriaRepository extends JpaRepository<CalificacionFinalMateria, String> {
}