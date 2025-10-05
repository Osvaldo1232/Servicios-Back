package com.primaria.app.repository;

import com.primaria.app.Model.ActividadAlumno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActividadAlumnoRepository extends JpaRepository<ActividadAlumno, String> {
    
    List<ActividadAlumno> findByAlumnoId(String alumnoId);

    List<ActividadAlumno> findByActividadId(String actividadId);
}