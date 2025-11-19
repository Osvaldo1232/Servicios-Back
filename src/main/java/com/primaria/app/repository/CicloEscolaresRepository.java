package com.primaria.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.primaria.app.Model.CicloEscolar;
import com.primaria.app.Model.Estatus;
public interface CicloEscolaresRepository extends JpaRepository<CicloEscolar, String>{
	CicloEscolar findTopByOrderByFechaCreadoDesc();
	Optional<CicloEscolar> findByEstatus(Estatus estatus);

	 Optional<CicloEscolar> findByAnioInicioAndAnioFin(int anioInicio, int anioFin);
	 
	 
	  @Query("""
		        SELECT DISTINCT c
		        FROM InscritoAlumno ia
		        JOIN ia.asignacion a
		        JOIN a.ciclo c
		        LEFT JOIN AlumnoTutor at 
		               ON at.alumno.id = ia.alumno.id 
		              AND at.ciclo.id = c.id
		        WHERE ia.alumno.id = :idAlumno
		          AND at.id IS NULL
		    """)
		    List<CicloEscolar> obtenerCiclosSinTutorAsignado(String idAlumno);
}
