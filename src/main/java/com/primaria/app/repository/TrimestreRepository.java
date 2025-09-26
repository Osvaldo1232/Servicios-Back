package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.primaria.app.Model.Trimestres;
import com.primaria.app.Model.Estatus;


public interface TrimestreRepository extends JpaRepository<Trimestres, String> {
	 List<Trimestres> findByEstatus(Estatus estatus);
}