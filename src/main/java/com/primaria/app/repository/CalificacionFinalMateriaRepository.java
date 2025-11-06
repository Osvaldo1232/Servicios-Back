package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.primaria.app.DTO.CalificacionTotalAlumnoDTO;
import com.primaria.app.Model.CalificacionFinalMateria;

@Repository
public interface CalificacionFinalMateriaRepository extends JpaRepository<CalificacionFinalMateria, String> {
	List<CalificacionFinalMateria> findByAlumno_IdAndCicloEscolar_Id(String alumnoId, String cicloId);
	@Query(value = """
	        SELECT
	            u.id AS idAlumno,
	            CONCAT(u.nombre, ' ', u.apellido_paterno, ' ', u.apellido_materno) AS nombreAlumno,
	            g.nombre AS grado,
	            gr.nombre AS grupo,
	            CONCAT(ce.anio_inicio, '-', ce.anio_fin) AS ciclo,
	            ROUND(AVG(cfm.promedio), 2) AS calificacionTotal
	        FROM calificacion_final_materia cfm
	        INNER JOIN estudiante e ON cfm.id_alumno = e.id
	        INNER JOIN usuario u ON e.id = u.id
	        INNER JOIN inscrito_alumno ia ON ia.id_alumno = e.id
	        INNER JOIN asignacion_docente_grado_grupo adgg ON adgg.id = ia.id_asignacion
	        INNER JOIN grado g ON adgg.id_grado = g.id
	        INNER JOIN grupo gr ON adgg.id_grupo = gr.id
	        INNER JOIN ciclo_escolar ce ON cfm.id_ciclo_escolar = ce.id
	        WHERE cfm.id_ciclo_escolar = :cicloId
	        GROUP BY u.id, u.nombre, u.apellido_paterno, u.apellido_materno, g.nombre, gr.nombre, ce.anio_inicio, ce.anio_fin
	        ORDER BY nombreAlumno
	    """, nativeQuery = true)
	    List<Object[]> obtenerPromedioPorCiclo(@Param("cicloId") String cicloId);


}