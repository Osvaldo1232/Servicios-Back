package com.primaria.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.primaria.app.Model.AlumnoTutor;




@Repository
public interface AlumnoTutorRepository extends JpaRepository<AlumnoTutor, String> {
	Optional<AlumnoTutor> findByAlumnoIdAndCicloId(String alumnoId, String cicloId);
    Optional<AlumnoTutor> findByAlumno_IdAndCiclo_Id(String idAlumno, String idCiclo);
    boolean existsByAlumno_IdAndCiclo_Id(String alumnoId, String cicloId);

    
    boolean existsByAlumno_IdAndTutor_IdAndCiclo_Id(String alumnoId, String tutorId, String cicloId);
}
