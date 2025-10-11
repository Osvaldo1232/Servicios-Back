package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.primaria.app.Model.Actividad;

public interface actividadRepository extends JpaRepository<Actividad, String> {

	 @Query("SELECT a FROM Actividad a " +
	           "WHERE (:docenteId IS NULL OR a.docente.id = :docenteId) " +
	           "AND (:asignacionId IS NULL OR a.asignacionMateriaGrado.id = :asignacionId) " +
	           "AND (:trimestreId IS NULL OR a.trimestre.id = :trimestreId) " +
	           "AND (:tipoEvaluacionId IS NULL OR a.tipoEvaluacion.id = :tipoEvaluacionId)")
	    List<Actividad> filtrar(
	            @Param("docenteId") String docenteId,
	            @Param("asignacionId") String asignacionId,
	            @Param("trimestreId") String trimestreId,
	            @Param("tipoEvaluacionId") String tipoEvaluacionId
	    );
}
