package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.primaria.app.Model.CampoFormativo;
import com.primaria.app.Model.Estatus;

@Repository

public interface CampoFormativoRepository extends JpaRepository<CampoFormativo, String> {
	 List<CampoFormativo> findByEstatus(Estatus estatus);
	  List<CampoFormativo> findByNombre(String nombre);
}