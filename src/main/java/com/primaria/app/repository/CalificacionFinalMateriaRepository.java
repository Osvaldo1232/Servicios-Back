package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.primaria.app.Model.CalificacionFinalMateria;

@Repository
public interface CalificacionFinalMateriaRepository extends JpaRepository<CalificacionFinalMateria, String> {
	List<CalificacionFinalMateria> findByAlumno_IdAndCicloEscolar_Id(String alumnoId, String cicloId);

}