package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.primaria.app.Model.Actividad;



public interface actividadRepository extends JpaRepository<Actividad, String> {
	
	 @Query("SELECT a FROM Actividad a " +
	           "WHERE (:docenteId IS NULL OR a.docente.id = :docenteId) " +
	           "AND (:gradoId IS NULL OR a.grado.id = :gradoId) " +
	           "AND (:grupoId IS NULL OR a.grupo.id = :grupoId) " +
	           "AND (:cicloId IS NULL OR a.ciclo.id = :cicloId) " +
	           "AND (:materiaId IS NULL OR a.materia.id = :materiaId)")
	    List<Actividad> filtrar(
	            @Param("docenteId") String docenteId,
	            @Param("gradoId") String gradoId,
	            @Param("grupoId") String grupoId,
	            @Param("cicloId") String cicloId,
	            @Param("materiaId") String materiaId
	    );
}