package com.primaria.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.primaria.app.Model.AlumnoTutor;




@Repository
public interface AlumnoTutorRepository extends JpaRepository<AlumnoTutor, String> {
    Optional<AlumnoTutor> findByAlumno_IdAndCiclo_Id(String idAlumno, String idCiclo);
}
