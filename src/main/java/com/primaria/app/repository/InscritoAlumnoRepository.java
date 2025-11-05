package com.primaria.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.primaria.app.Model.Estudiante;
import com.primaria.app.Model.InscritoAlumno;

public interface InscritoAlumnoRepository extends JpaRepository<InscritoAlumno, String> {

    // 🔹 Última inscripción por FECHA
    InscritoAlumno findTopByAlumno_IdOrderByFechaInscripcionDesc(String alumnoId);

    // 🔹 Última inscripción por CICLO (desde asignación)
    InscritoAlumno findTopByAlumno_IdOrderByAsignacion_Ciclo_AnioInicioDesc(String alumnoId);

    // 🔹 Buscar por ciclo dentro de la asignación
    List<InscritoAlumno> findByAsignacion_Ciclo_Id(String cicloId);

    // 🔹 Buscar por grado, grupo y ciclo (a través de la asignación)
    List<InscritoAlumno> findByAsignacion_Grado_IdAndAsignacion_Grupo_IdAndAsignacion_Ciclo_Id(
            String gradoId,
            String grupoId,
            String cicloId
    );

    // 🔹 Consulta personalizada: alumnos activos por ciclo, grado y grupo
    @Query("""
        SELECT i.alumno
        FROM InscritoAlumno i
        WHERE i.asignacion.ciclo.id = :idCiclo
          AND i.asignacion.grado.id = :idGrado
          AND i.asignacion.grupo.id = :idGrupo
          AND i.estatus = 'ACTIVO'
    """)
    List<Estudiante> findAlumnosPorCicloGradoGrupo(
        @Param("idCiclo") String idCiclo,
        @Param("idGrado") String idGrado,
        @Param("idGrupo") String idGrupo
    );
    
    Optional<InscritoAlumno> findByAlumnoId(String alumnoId);
}
