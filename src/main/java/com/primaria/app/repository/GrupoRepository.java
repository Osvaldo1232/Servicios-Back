package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, String> {
	 List<Grupo> findByNombre(String nombre);
	 
	 List<Grupo> findByEstatus(Estatus estatus);
}