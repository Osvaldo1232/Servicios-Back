package com.primaria.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.primaria.app.DTO.CalificacionMateriaDTO;
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
		      AND adgg.id_docente = :docenteId
		    GROUP BY 
		        u.id, u.nombre, u.apellido_paterno, u.apellido_materno, 
		        g.nombre, gr.nombre, ce.anio_inicio, ce.anio_fin
		    ORDER BY nombreAlumno
		    """, nativeQuery = true)
		List<Object[]> obtenerPromedioPorCicloYDocente(
		        @Param("cicloId") String cicloId,
		        @Param("docenteId") String docenteId
		);

	    List<CalificacionFinalMateria> findByAlumnoId(String alumnoId);

	    
	    @Query(value = """
	            SELECT 
	                cam.nombre AS campoFormativo,
	                ROUND(AVG(cfm.promedio), 2) AS promedioCampo
	            FROM calificacion_final_materia cfm
	            JOIN materia m ON cfm.id_materia = m.id
	            JOIN campo_formativo cam ON m.campo_formativo = cam.id
	            WHERE cfm.id_ciclo_escolar = :idCiclo
	            GROUP BY cam.id, cam.nombre
	            """, nativeQuery = true)
	        List<Object[]> obtenerPromedioPorCampo(@Param("idCiclo") String idCiclo);

	        @Query(value = """
	            SELECT 
	                ROUND(AVG(cfm.promedio), 2) AS promedioGeneral
	            FROM calificacion_final_materia cfm
	            WHERE cfm.id_ciclo_escolar = :idCiclo
	            """, nativeQuery = true)
	        Double obtenerPromedioGeneral(@Param("idCiclo") String idCiclo);
	        Optional<CalificacionFinalMateria> findByAlumnoIdAndMateriaIdAndCicloEscolarId(
	                String idAlumno, String idMateria, String idCiclo);

	        @Query("""
	                SELECT new com.primaria.app.DTO.CalificacionMateriaDTO(
	                    g.id, g.nombre,
	                    m.id, m.nombre,
	                    cf.id, cf.nombre,
	                    cfm.promedio
	                )
	                FROM CalificacionFinalMateria cfm
	                JOIN cfm.grado g
	                JOIN cfm.materia m
	                JOIN m.campoFormativo cf
	                WHERE cfm.alumno.id = :idAlumno
	                ORDER BY g.nombre, m.nombre
	            """)
	            List<CalificacionMateriaDTO> obtenerPromediosPorAlumno(@Param("idAlumno") String idAlumno);
	        
	        
	        
	        
	        @Query("""
	                SELECT c 
	                FROM CalificacionFinalMateria c
	                WHERE c.alumno.id = :idAlumno
	                AND c.grado.id = :idGrado
	                AND c.cicloEscolar.id = :idCiclo
	            """)
	            List<CalificacionFinalMateria> obtenerCalificacionesAlumno(
	                    @Param("idAlumno") String idAlumno,
	                    @Param("idGrado") String idGrado,
	                    @Param("idCiclo") String idCiclo
	            );
}