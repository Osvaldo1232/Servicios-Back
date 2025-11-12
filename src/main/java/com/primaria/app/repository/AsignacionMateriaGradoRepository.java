package com.primaria.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.primaria.app.DTO.MateriaCalificacionResDTO;
import com.primaria.app.Model.AsignacionMateriaGrado;

public interface AsignacionMateriaGradoRepository extends JpaRepository<AsignacionMateriaGrado, String> {

    List<AsignacionMateriaGrado> findByGradoId(String idGrado);

    @Query("""
        SELECT new com.primaria.app.DTO.MateriaCalificacionResDTO(
            m.id,
            m.nombre,
            c.promedio,
            g.id,
            g.nombre,
            cf.nombre
        )
        FROM AsignacionMateriaGrado amg
        JOIN amg.materia m
        JOIN amg.grado g
        JOIN m.campoFormativo cf
        LEFT JOIN CalificacionFinalMateria c
            ON c.materia.id = m.id
            AND c.grado.id = g.id
            AND c.alumno.id = :idAlumno
            AND c.cicloEscolar.id = :idCicloEscolar
        WHERE g.id = :idGrado
        ORDER BY cf.nombre, m.nombre
    """)
    List<MateriaCalificacionResDTO> obtenerMateriasYPromedioPorGrado(
            @Param("idGrado") String idGrado,
            @Param("idAlumno") String idAlumno,
            @Param("idCicloEscolar") String idCicloEscolar
    );
}
