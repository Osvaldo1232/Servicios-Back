package com.primaria.app.repository;

import com.primaria.app.Model.Calificacion_final;
import com.primaria.app.Model.Estudiante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionFinalRepository extends JpaRepository<Calificacion_final, String> {
    
   
    Optional<Calificacion_final> findByAlumnoIdAndMateriaIdAndTrimestreIdAndCicloId(
            String idAlumno, String idMateria, String idTrimestre, String idCiclo);
    
    
    List<Calificacion_final> findByCicloIdAndAlumnoIn(String idCiclo, List<Estudiante> alumnos);
    
    
    List<Calificacion_final> findByAlumno_IdAndCiclo_Id(String alumnoId, String cicloId);
    
    List<Calificacion_final> findByGradoId(String gradoId);
    
   
    
    @Query(value = """
            SELECT cf
            FROM Calificacion_final cf
            JOIN FETCH cf.materia m
            JOIN FETCH m.campoFormativo cam
            JOIN FETCH cf.trimestre t
            JOIN FETCH cf.alumno a
            JOIN FETCH cf.grado g
            JOIN FETCH cf.ciclo c
            WHERE a.id = :idAlumno
            ORDER BY c.id ASC, g.id ASC, m.nombre ASC, t.nombre ASC
        """)
        List<Calificacion_final> findAllByAlumnoOrdenado(@Param("idAlumno") String idAlumno);
        
        
        
        @Query("SELECT c FROM Calificacion_final c " +
        	       "WHERE c.alumno.id = :idAlumno AND c.materia.id = :idMateria AND c.ciclo.id = :idCiclo")
        	List<Calificacion_final> findAllByAlumnoAndMateriaAndCiclo(
        	        @Param("idAlumno") String idAlumno,
        	        @Param("idMateria") String idMateria,
        	        @Param("idCiclo") String idCiclo);
}
