package com.primaria.app.repository;

import com.primaria.app.DTO.TrimestreMateriaAlumnoDTO;
import com.primaria.app.Model.Calificacion_final;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificacionMFinalRepository extends JpaRepository<Calificacion_final, String> {

    @Query("""
        SELECT new com.primaria.app.DTO.TrimestreMateriaAlumnoDTO(
            a.nombre,
            g.nombre,
            m.nombre,
            MAX(CASE WHEN t.nombre = 'Trimestre 1' THEN c.promedio ELSE NULL END),
            MAX(CASE WHEN t.nombre = 'Trimestre 2' THEN c.promedio ELSE NULL END),
            MAX(CASE WHEN t.nombre = 'Trimestre 3' THEN c.promedio ELSE NULL END),
            AVG(c.promedio)
        )
        FROM Calificacion_final c
        JOIN c.alumno a
        JOIN c.materia m
        JOIN c.grado g
        JOIN c.trimestre t
        JOIN c.ciclo ci
        WHERE a.id = :idAlumno
          AND (:idGrado IS NULL OR g.id = :idGrado)
          AND (:idMateria IS NULL OR m.id = :idMateria)
          AND (:idCiclo IS NULL OR ci.id = :idCiclo)
        GROUP BY a.nombre, g.nombre, m.nombre
        ORDER BY m.nombre
    """)
    List<TrimestreMateriaAlumnoDTO> obtenerCalificacionesPorFiltros(
            @Param("idAlumno") String idAlumno,
            @Param("idGrado") String idGrado,
            @Param("idMateria") String idMateria,
            @Param("idCiclo") String idCiclo
    );
}
