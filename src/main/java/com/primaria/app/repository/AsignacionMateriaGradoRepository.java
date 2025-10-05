package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.primaria.app.Model.AsignacionMateriaGrado;

public interface AsignacionMateriaGradoRepository extends JpaRepository<AsignacionMateriaGrado, String> {
	List<AsignacionMateriaGrado> findByGradoId(String idGrado);
}