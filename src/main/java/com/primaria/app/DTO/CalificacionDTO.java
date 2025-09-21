package com.primaria.app.DTO;

import java.util.UUID;

public class CalificacionDTO {
	private UUID id;
    private UUID actividadId;
    private UUID alumnoId;
    private Double calificacion;
    private String cicloId; 
    // Constructores

    public CalificacionDTO() {}

    public CalificacionDTO(UUID id, UUID actividadId, UUID alumnoId, Double calificacion) {
        this.id = id;
        this.actividadId = actividadId;
        this.alumnoId = alumnoId;
        this.calificacion = calificacion;
    }

    // Getters y setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getActividadId() {
        return actividadId;
    }

    public void setActividadId(UUID actividadId) {
        this.actividadId = actividadId;
    }

    public UUID getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(UUID alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
    
    public String getCicloId() {
        return cicloId;
    }

    public void setCicloId(String cicloId) {
        this.cicloId = cicloId;
    }
}
