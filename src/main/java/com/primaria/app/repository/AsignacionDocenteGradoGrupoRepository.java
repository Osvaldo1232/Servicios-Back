package com.primaria.app.repository;

import com.primaria.app.Model.AsignacionDocenteGradoGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsignacionDocenteGradoGrupoRepository extends JpaRepository<AsignacionDocenteGradoGrupo, String> {

    @Query("SELECT a FROM AsignacionDocenteGradoGrupo a " +
           "WHERE (:docenteId IS NULL OR a.docente.id = :docenteId) " +
           "AND (:gradoId IS NULL OR a.grado.id = :gradoId) " +
           "AND (:grupoId IS NULL OR a.grupo.id = :grupoId) " +
           "AND (:cicloId IS NULL OR a.ciclo.id = :cicloId)")
    List<AsignacionDocenteGradoGrupo> filtrar(
            @Param("docenteId") String docenteId,
            @Param("gradoId") String gradoId,
            @Param("grupoId") String grupoId,
            @Param("cicloId") String cicloId
    );
}
