package com.primaria.app.repository;


import com.primaria.app.Model.CalificacionFinalMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReprobadosRepository extends JpaRepository<CalificacionFinalMateria, String> {

    @Query("""
        SELECT 
            a.id AS idAlumno,
            CONCAT(a.nombre, ' ', a.apellidoPaterno, ' ', a.apellidoMaterno) AS nombreCompleto,
            g.nombre AS grado,
            gr.nombre AS grupo,
            CONCAT(c.anioInicio, '-', c.anioFin) AS ciclo,
            m.nombre AS materia,
            cfm.promedio AS promedio
        FROM CalificacionFinalMateria cfm
        JOIN cfm.alumno a
        JOIN InscritoAlumno ia ON ia.alumno.id = a.id
        JOIN ia.asignacion adgg
        JOIN adgg.grado g
        JOIN adgg.grupo gr
        JOIN adgg.ciclo c
        JOIN cfm.materia m
        WHERE cfm.promedio < 6
          AND ia.estatus = 'ACTIVO'
          AND adgg.estatus = 'ACTIVO'
          AND adgg.id = :idAsignacion
          AND cfm.cicloEscolar.id = adgg.ciclo.id
    """)
    List<Object[]> obtenerReprobadosPorAsignacion(@Param("idAsignacion") String idAsignacion);
}
