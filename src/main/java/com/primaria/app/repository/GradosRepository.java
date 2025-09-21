package com.primaria.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.primaria.app.Model.Grado;
import com.primaria.app.Model.Grupo;




public interface GradosRepository extends JpaRepository<Grado, String> {
}