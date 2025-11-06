package com.primaria.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.primaria.app.DTO.CalificacionAlumnoProjection;
import com.primaria.app.Model.Calificacion_final;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Calificacion_final, String> {

    @Query(value = """
       SELECT 
        a.id AS id_alumno,
        CONCAT(u.nombre, ' ', u.apellido_paterno, ' ', u.apellido_materno) AS nombre_alumno,
        m.nombre AS nombre_materia,
        ROUND(MAX(CASE WHEN t.nombre = 'tri' THEN cf.promedio END), 2) AS trimestre_1,
        ROUND(MAX(CASE WHEN t.nombre = 'trime 2' THEN cf.promedio END), 2) AS trimestre_2,
        ROUND(MAX(CASE WHEN t.nombre = 'trime 3' THEN cf.promedio END), 2) AS trimestre_3,
        ROUND(AVG(cf.promedio), 2) AS promedio_final
    FROM inscrito_alumno ia
    INNER JOIN asignacion_docente_grado_grupo adgg ON ia.id_asignacion = adgg.id
    INNER JOIN estudiante a ON ia.id_alumno = a.id
    INNER JOIN usuario u ON a.id = u.id
    INNER JOIN calificacion_final cf 
        ON cf.id_alumno = a.id
       AND cf.id_grado = adgg.id_grado
       AND cf.id_ciclo = adgg.id_ciclo
    INNER JOIN materia m ON cf.id_materia = m.id
    INNER JOIN trimestres t ON cf.id_trimestre = t.id
    WHERE adgg.id_grado = :gradoId
    GROUP BY a.id, u.nombre, u.apellido_paterno, u.apellido_materno, m.nombre
    ORDER BY nombre_alumno, nombre_materia
""", nativeQuery = true) 
    List<CalificacionAlumnoProjection> obtenerCalificacionesPorGrado(@Param("gradoId") String gradoId);
}
