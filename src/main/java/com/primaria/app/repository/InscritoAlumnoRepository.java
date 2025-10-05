package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.primaria.app.Model.InscritoAlumno;

public interface InscritoAlumnoRepository extends JpaRepository<InscritoAlumno, String> {
	
	List<InscritoAlumno> findByGradoId(String idGrado);
	List<InscritoAlumno> findByGrupoId(String idGrupo);
	List<InscritoAlumno> findByCicloId(String idCiclo);

	
	  @Query("SELECT i FROM InscritoAlumno i " +
	           "WHERE (:gradoId IS NULL OR i.grado.id = :gradoId) " +
	           "AND (:grupoId IS NULL OR i.grupo.id = :grupoId) " +
	           "AND (:cicloId IS NULL OR i.ciclo.id = :cicloId)")
	    List<InscritoAlumno> filtrar(
	            @Param("gradoId") String gradoId,
	            @Param("grupoId") String grupoId,
	            @Param("cicloId") String cicloId
	    );
}

