package com.primaria.app.Service;

import com.primaria.app.DTO.CalificacionAlumnoProjection;
import com.primaria.app.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service

public class ConsultaCalificacionService {

	 private final ConsultaRepository calificacionRepository;

	    public ConsultaCalificacionService(ConsultaRepository calificacionRepository) {
	        this.calificacionRepository = calificacionRepository;
	    }

	    public List<CalificacionAlumnoProjection> obtenerPorGrado(String gradoId) {
	        return calificacionRepository.obtenerCalificacionesPorGrado(gradoId);
	    }
}
