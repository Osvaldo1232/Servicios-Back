package com.primaria.app.DTO;

public class ActividadAlumnoDTO {

    private String actividadId;
    private String alumnoId;
    private Double valorObtenido;

    public String getActividadId() { return actividadId; }
    public void setActividadId(String actividadId) { this.actividadId = actividadId; }

    public String getAlumnoId() { return alumnoId; }
    public void setAlumnoId(String alumnoId) { this.alumnoId = alumnoId; }

    public Double getValorObtenido() { return valorObtenido; }
    public void setValorObtenido(Double valorObtenido) { this.valorObtenido = valorObtenido; }
}
