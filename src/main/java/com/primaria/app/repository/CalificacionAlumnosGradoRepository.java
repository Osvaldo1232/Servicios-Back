package com.primaria.app.repository;

import com.primaria.app.Model.Calificacion_final;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface CalificacionAlumnosGradoRepository extends JpaRepository<Calificacion_final, String> {

	@Query(value = """
	        SELECT 
	            m.nombre AS nombre_materia,
	            e.id AS id_alumno,
	            CONCAT(u.nombre, ' ', u.apellido_paterno, ' ', u.apellido_materno) AS nombre_alumno,

	            MAX(CASE WHEN t.nombre = 'Trimestre 1' THEN t.id END) AS id_trimestre_1,
	            MAX(CASE WHEN t.nombre = 'Trimestre 1' THEN c.id END) AS id_calificacion_1,
	            MAX(CASE WHEN t.nombre = 'Trimestre 1' THEN c.promedio END) AS trimestre_1,

	            MAX(CASE WHEN t.nombre = 'Trimestre 2' THEN t.id END) AS id_trimestre_2,
	            MAX(CASE WHEN t.nombre = 'Trimestre 2' THEN c.id END) AS id_calificacion_2,
	            MAX(CASE WHEN t.nombre = 'Trimestre 2' THEN c.promedio END) AS trimestre_2,

	            MAX(CASE WHEN t.nombre = 'Trimestre 3' THEN t.id END) AS id_trimestre_3,
	            MAX(CASE WHEN t.nombre = 'Trimestre 3' THEN c.id END) AS id_calificacion_3,
	            MAX(CASE WHEN t.nombre = 'Trimestre 3' THEN c.promedio END) AS trimestre_3,

	            ROUND(AVG(c.promedio), 2) AS promedio_final

	        FROM calificacion_final c
	        INNER JOIN estudiante e ON e.id = c.id_alumno
	        INNER JOIN usuario u ON u.id = e.id
	        INNER JOIN materia m ON m.id = c.id_materia
	        INNER JOIN trimestres t ON t.id = c.id_trimestre
	        WHERE c.id_ciclo = :idCiclo
	          AND c.id_grado = :idGrado
	          AND c.id_materia = :idMateria
	        GROUP BY e.id, m.id, u.nombre, u.apellido_paterno, u.apellido_materno
	        ORDER BY nombre_alumno
	        """, nativeQuery = true)
	List<Object[]> obtenerCalificacionesAgrupadas(
	        @Param("idCiclo") String idCiclo,
	        @Param("idGrado") String idGrado,
	        @Param("idMateria") String idMateria
	);

}
