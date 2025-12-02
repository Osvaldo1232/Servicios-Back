package com.primaria.app.repository;

import com.primaria.app.Model.CalificacionFinalMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstadisticasRepository extends JpaRepository<CalificacionFinalMateria, String> {

    @Query("""
        SELECT ia.alumno.id
        FROM InscritoAlumno ia
        WHERE ia.asignacion.id = :idAsignacion
          AND ia.estatus = 'ACTIVO'
    """)
    List<String> obtenerAlumnosInscritos(@Param("idAsignacion") String idAsignacion);

    @Query("""
        SELECT cfm.alumno.id, cfm.promedio
        FROM CalificacionFinalMateria cfm
        WHERE cfm.alumno.id IN :idsAlumnos
    """)
    List<Object[]> obtenerPromediosPorAlumno(@Param("idsAlumnos") List<String> idsAlumnos);
    
    
    
    
    
    @Query("""
    	    SELECT c.alumno.id, 
    	           CONCAT( u.apellidoPaterno, ' ', u.apellidoMaterno, ' ',u.nombre, ' '),
    	           c.materia.nombre,
    	           c.promedio
    	    FROM InscritoAlumno i
    	    JOIN i.alumno a
    	    JOIN Usuario u ON u.id = a.id
    	    JOIN CalificacionFinalMateria c ON c.alumno.id = a.id
    	    WHERE i.asignacion.id = :idAsignacion
    	    ORDER BY u.apellidoPaterno DESC, u.apellidoMaterno DESC, u.nombre DESC
    	""")
    	List<Object[]> obtenerMateriasYCalificaciones(String idAsignacion);

}
