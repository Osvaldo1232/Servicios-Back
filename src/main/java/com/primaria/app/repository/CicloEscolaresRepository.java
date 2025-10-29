package com.primaria.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.primaria.app.Model.CicloEscolar;
public interface CicloEscolaresRepository extends JpaRepository<CicloEscolar, String>{
	CicloEscolar findTopByOrderByFechaCreadoDesc();
}
