package com.primaria.app.DTO;

import java.util.UUID;

public class AlumnoTutorDTO {

    private UUID id;
    private UUID alumnoId;
    private UUID tutorId;
    private UUID cicloId;
    private String parentesco; // Nuevo campo agregado

    // Constructor vacío
    public AlumnoTutorDTO() {}

    // Constructor con todos los campos
    public AlumnoTutorDTO(UUID id, UUID alumnoId, UUID tutorId, UUID cicloId, String parentesco) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.tutorId = tutorId;
        this.cicloId = cicloId;
        this.parentesco = parentesco;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(UUID alumnoId) {
        this.alumnoId = alumnoId;
    }

    public UUID getTutorId() {
        return tutorId;
    }

    public void setTutorId(UUID tutorId) {
        this.tutorId = tutorId;
    }

    public UUID getCicloId() {
        return cicloId;
    }

    public void setCicloId(UUID cicloId) {
        this.cicloId = cicloId;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
