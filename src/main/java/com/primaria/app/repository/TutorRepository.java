package com.primaria.app.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Tutor;


public interface TutorRepository extends JpaRepository<Tutor, String> {
	 List<Tutor> findByEstatus(Estatus estatus);
}