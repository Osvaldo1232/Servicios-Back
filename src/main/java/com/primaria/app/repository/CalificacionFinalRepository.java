package com.primaria.app.repository;

import com.primaria.app.Model.Calificacion_final;
import com.primaria.app.Model.Estudiante;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionFinalRepository extends JpaRepository<Calificacion_final, String> {
    
   
    Optional<Calificacion_final> findByAlumnoIdAndMateriaIdAndTrimestreIdAndCicloId(
            String idAlumno, String idMateria, String idTrimestre, String idCiclo);
    
    
    List<Calificacion_final> findByCicloIdAndAlumnoIn(String idCiclo, List<Estudiante> alumnos);
    
    
    List<Calificacion_final> findByAlumno_IdAndCiclo_Id(String alumnoId, String cicloId);
    
    List<Calificacion_final> findByGradoId(String gradoId);
    
}
