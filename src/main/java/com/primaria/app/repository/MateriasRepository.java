package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.primaria.app.Model.Materia;


public interface MateriasRepository extends JpaRepository<Materia, String> {
	 List<Materia> findByCampoFormativo_Id(String idCampoFormativo);
	 
	 boolean existsByNombreIgnoreCase(String nombre);
	 
	 boolean existsByNombreIgnoreCaseAndIdNot(String nombre, String id);
}
