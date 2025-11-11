package com.primaria.app.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primaria.app.DTO.AlumnoTutorDTO;
import com.primaria.app.DTO.TutorDTO;
import com.primaria.app.DTO.TutorResumenDTO;
import com.primaria.app.Model.AlumnoTutor;
import com.primaria.app.Model.CicloEscolar;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.Tutor;
import com.primaria.app.repository.AlumnoTutorRepository;
import com.primaria.app.repository.CicloEscolaresRepository;
import com.primaria.app.repository.EstudianteRepository;
import com.primaria.app.repository.TutorRepository;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private CicloEscolaresRepository cicloEscolarRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;
    
    @Autowired
    private AlumnoTutorRepository alumnoTutorRepository;
    @Autowired
    private ModelMapper modelMapper;

    // ✅ Listar todos los tutores
    public List<TutorDTO> listarTodos() {
        return tutorRepository.findAll()
                .stream()
                .map(tutor -> modelMapper.map(tutor, TutorDTO.class))
                .collect(Collectors.toList());
    }

    // ✅ Obtener tutor por UUID
    public Optional<TutorDTO> obtenerPorUuid(String uuid) {
        return tutorRepository.findById(uuid)
                .map(tutor -> modelMapper.map(tutor, TutorDTO.class));
    }

    // ✅ Guardar nuevo tutor
    public TutorDTO guardar(TutorDTO dto) {
        Tutor tutor = modelMapper.map(dto, Tutor.class);
        tutor.setId(UUID.randomUUID().toString());
        Tutor guardado = tutorRepository.save(tutor);
        return modelMapper.map(guardado, TutorDTO.class);
    }

    // ✅ Guardar entidad directamente (por si se usa fuera del flujo DTO)
    public Tutor save(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    // ✅ Actualizar tutor existente
    public boolean actualizar(String uuid, TutorDTO dto) {
        Optional<Tutor> existente = tutorRepository.findById(uuid);
        if (existente.isPresent()) {
            Tutor tutor = existente.get();
            tutor.setNombre(dto.getNombre());
            tutor.setApellidoPaterno(dto.getApellidoPaterno());
            tutor.setApellidoMaterno(dto.getApellidoMaterno());
            tutor.setCorreo(dto.getCorreo());
            tutor.setTelefono(dto.getTelefono());
            tutor.setEstatus(dto.getEstatus());
            tutorRepository.save(tutor);
            return true;
        }
        return false;
    }

    // ✅ Eliminar tutor por UUID
    public boolean eliminar(String uuid) {
        Optional<Tutor> existente = tutorRepository.findById(uuid);
        if (existente.isPresent()) {
            tutorRepository.delete(existente.get());
            return true;
        }
        return false;
    }

    // ✅ Obtener lista resumida de tutores activos
    public List<TutorResumenDTO> obtenerActivos() {
        List<Tutor> activos = tutorRepository.findByEstatus(Estatus.ACTIVO);
        return activos.stream()
                .map(t -> new TutorResumenDTO(
                        t.getId(),
                        String.format("%s %s %s",
                                t.getNombre(),
                                t.getApellidoPaterno() != null ? t.getApellidoPaterno() : "",
                                t.getApellidoMaterno() != null ? t.getApellidoMaterno() : "").trim()
                ))
                .collect(Collectors.toList());
    }

    // ✅ Cambiar estatus (ACTIVO ↔ INACTIVO)
    public boolean cambiarEstatus(String uuid) {
        Optional<Tutor> optionalTutor = tutorRepository.findById(uuid);
        if (optionalTutor.isPresent()) {
            Tutor tutor = optionalTutor.get();
            tutor.setEstatus(
                    tutor.getEstatus() == Estatus.ACTIVO
                            ? Estatus.INACTIVO
                            : Estatus.ACTIVO
            );
            tutorRepository.save(tutor);
            return true;
        }
        return false;
    }
    
   public Map<String, Object> guardarRelacion(AlumnoTutorDTO dto) {

    var alumno = estudianteRepository.findById(dto.getAlumnoId().toString())
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado con ID: " + dto.getAlumnoId()));

    var tutor = tutorRepository.findById(dto.getTutorId().toString())
            .orElseThrow(() -> new RuntimeException("Tutor no encontrado con ID: " + dto.getTutorId()));

    var ciclo = cicloEscolarRepository.findById(dto.getCicloId().toString())
            .orElseThrow(() -> new RuntimeException("Ciclo escolar no encontrado con ID: " + dto.getCicloId()));

    // 🔍 Verificar si ya existe la relación
    boolean existeRelacion = alumnoTutorRepository.existsByAlumno_IdAndTutor_IdAndCiclo_Id(
            dto.getAlumnoId().toString(),
            dto.getTutorId().toString(),
            dto.getCicloId().toString()
    );

    if (existeRelacion) {
        throw new RuntimeException("Ya existe una relación entre este alumno, tutor y ciclo escolar.");
    }

    AlumnoTutor relacion = new AlumnoTutor();
    relacion.setAlumno(alumno);
    relacion.setTutor(tutor);
    relacion.setCiclo(ciclo);
    relacion.setParentesco(dto.getParentesco());

    AlumnoTutor guardado = alumnoTutorRepository.save(relacion);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Relación Alumno-Tutor registrada exitosamente");
    response.put("idAsignacion", guardado.getId());

    return response;
}

}
