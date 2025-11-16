package com.primaria.app.repository;

import com.primaria.app.Model.AsignacionDocenteGradoGrupo;
import com.primaria.app.Model.Estatus;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface AsignacionDocenteGradoGrupoRepository extends JpaRepository<AsignacionDocenteGradoGrupo, String> {
	List<AsignacionDocenteGradoGrupo> findByCiclo_Id(String cicloId);

	 List<AsignacionDocenteGradoGrupo> findByDocenteId(String docenteId);
	 
	  List<AsignacionDocenteGradoGrupo> findByDocente_IdAndEstatus(String idDocente, Estatus estatus);
	  
	  
	  AsignacionDocenteGradoGrupo findTopByDocente_IdOrderByFechaCreadoDesc(String idDocente);
	  
	  
	  
	  Optional<AsignacionDocenteGradoGrupo> findByDocente_IdAndGrado_IdAndGrupo_IdAndCiclo_Id(
		        String docenteId,
		        String gradoId,
		        String grupoId,
		        String cicloId
		);
	  Optional<AsignacionDocenteGradoGrupo> findByGrado_IdAndGrupo_IdAndCiclo_Id(
		        String gradoId,
		        String grupoId,
		        String cicloId
		);

}
