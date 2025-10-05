package com.primaria.app.repository;

import com.primaria.app.Model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, String> {

    @Query("SELECT c FROM Calificacion c " +
           "WHERE (:alumnoId IS NULL OR c.alumno.id = :alumnoId) " +
           "AND (:materiaId IS NULL OR c.actividad.materia.id = :materiaId) " +
           "AND (:trimestreId IS NULL OR c.actividad.trimestre.id = :trimestreId) " +
           "AND (:cicloId IS NULL OR c.ciclo.id = :cicloId)")
    List<Calificacion> filtrar(
            @Param("alumnoId") String alumnoId,
            @Param("materiaId") String materiaId,
            @Param("trimestreId") String trimestreId,
            @Param("cicloId") String cicloId
    );
}
