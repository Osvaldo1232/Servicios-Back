package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Tipos_Evaluacion;


public interface Tipos_EvaluacionRepository extends JpaRepository<Tipos_Evaluacion, String> {
	 List<Tipos_Evaluacion> findByEstatus(Estatus estatus);
}