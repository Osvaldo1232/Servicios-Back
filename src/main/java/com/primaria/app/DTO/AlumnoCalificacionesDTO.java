package com.primaria.app.DTO;

import java.util.Map;

public class AlumnoCalificacionesDTO {

    private String idAlumno;
    private String nombreAlumno;
    private Map<String, Double> calificacionesPorTrimestre; // clave: nombreTrimestre, valor: calificación

    public AlumnoCalificacionesDTO() {}

    public AlumnoCalificacionesDTO(String idAlumno, String nombreAlumno, Map<String, Double> calificacionesPorTrimestre) {
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.calificacionesPorTrimestre = calificacionesPorTrimestre;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public Map<String, Double> getCalificacionesPorTrimestre() {
        return calificacionesPorTrimestre;
    }

    public void setCalificacionesPorTrimestre(Map<String, Double> calificacionesPorTrimestre) {
        this.calificacionesPorTrimestre = calificacionesPorTrimestre;
    }
}
