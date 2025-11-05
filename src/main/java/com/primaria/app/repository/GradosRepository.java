package com.primaria.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.primaria.app.Model.Grado;





public interface GradosRepository extends JpaRepository<Grado, String> {
	List<Grado> findByNombre(String nombre);

}