package com.primaria.app.repository;

import com.primaria.app.Model.AsignacionDocenteGradoGrupo;
import com.primaria.app.Model.Estatus;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AsignacionDocenteGradoGrupoRepository extends JpaRepository<AsignacionDocenteGradoGrupo, String> {
	List<AsignacionDocenteGradoGrupo> findByCiclo_Id(String cicloId);

	 List<AsignacionDocenteGradoGrupo> findByDocenteId(String docenteId);
	 
	  List<AsignacionDocenteGradoGrupo> findByDocente_IdAndEstatus(String idDocente, Estatus estatus);
	  
	  
	  AsignacionDocenteGradoGrupo findTopByDocente_IdOrderByFechaCreadoDesc(String idDocente);
}
