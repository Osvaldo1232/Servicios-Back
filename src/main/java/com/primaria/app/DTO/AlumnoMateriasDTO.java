package com.primaria.app.DTO;

import java.util.List;

public class AlumnoMateriasDTO {

    private String idAlumno;
    private String nombreAlumno;
    private List<MateriasCalificacionDTO> materias;

    public AlumnoMateriasDTO(String idAlumno, String nombreAlumno, List<MateriasCalificacionDTO> materias) {
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.materias = materias;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public List<MateriasCalificacionDTO> getMaterias() {
        return materias;
    }
}
