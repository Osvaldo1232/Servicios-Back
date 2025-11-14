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
}
