package com.primaria.app.repository;

import com.primaria.app.DTO.DocenteMateriaDTO;
import com.primaria.app.Model.AsignacionDocenteGradoGrupo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface DocenteGradoGrupoRepository extends JpaRepository<AsignacionDocenteGradoGrupo, String> {

	@Query("""
		    SELECT new com.primaria.app.DTO.DocenteMateriaDTO(
		        adg.grado.nombre,
		        adg.grupo.nombre,
		        (
		            SELECT amg.materia.nombre
		            FROM AsignacionMateriaGrado amg
		            WHERE amg.grado.id = adg.grado.id
		        )
		    )
		    FROM AsignacionDocenteGradoGrupo adg
		    WHERE adg.docente.id = :idDocente
		      AND adg.ciclo.id = :idCiclo
		""")
		List<DocenteMateriaDTO> obtenerMateriasPorDocenteYCiclo(
		    @Param("idDocente") String idDocente,
		    @Param("idCiclo") String idCiclo
		);


}
