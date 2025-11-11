package com.primaria.app.DTO;

public class AlumnoInscritoDTO {
    private String idAlumno;
    private String nombreCompleto;

    public AlumnoInscritoDTO(String idAlumno, String nombreCompleto) {
        this.idAlumno = idAlumno;
        this.nombreCompleto = nombreCompleto;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
}
