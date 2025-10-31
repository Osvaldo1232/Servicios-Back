package com.primaria.app.repository;

import com.primaria.app.Model.AsignacionDocenteGradoGrupo;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AsignacionDocenteGradoGrupoRepository extends JpaRepository<AsignacionDocenteGradoGrupo, String> {
	List<AsignacionDocenteGradoGrupo> findByCiclo_Id(String cicloId);

   
}
