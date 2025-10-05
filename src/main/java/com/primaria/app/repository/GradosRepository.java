package com.primaria.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.primaria.app.Model.Grado;





public interface GradosRepository extends JpaRepository<Grado, String> {
}