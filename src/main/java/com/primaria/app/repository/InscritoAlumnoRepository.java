package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.InscritoAlumno;

public interface InscritoAlumnoRepository extends JpaRepository<InscritoAlumno, String> {
	InscritoAlumno findTopByAlumno_IdOrderByFechaInscripcionDesc(String alumnoId);
	List<InscritoAlumno> findByGradoId(String idGrado);
	List<InscritoAlumno> findByGrupoId(String idGrupo);
	List<InscritoAlumno> findByCicloId(String idCiclo);

    List<InscritoAlumno> findByGrado_IdAndGrupo_IdAndCiclo_Id(String gradoId, String grupoId, String cicloId);
    
   
	  
	  
	  @Query("""
		        SELECT ia
		        FROM InscritoAlumno ia
		        WHERE ia.alumno.id = :idAlumno
		        ORDER BY ia.ciclo.fechaInicio DESC
		        LIMIT 1
		    """)
		    InscritoAlumno encontrarUltimaInscripcionPorAlumno(@Param("idAlumno") String idAlumno);
	  
	  
	  
	  @Query("""
		        SELECT i.alumno
		        FROM InscritoAlumno i
		        WHERE i.ciclo.id = :idCiclo
		          AND i.grado.id = :idGrado
		          AND i.grupo.id = :idGrupo
		          AND i.estatus = 'ACTIVO'
		    """)
		    List<Estudiante> findAlumnosPorCicloGradoGrupo(
		        @Param("idCiclo") String idCiclo,
		        @Param("idGrado") String idGrado,
		        @Param("idGrupo") String idGrupo
		    );
	  
	  
}

