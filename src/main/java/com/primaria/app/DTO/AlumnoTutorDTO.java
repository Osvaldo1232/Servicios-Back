package com.primaria.app.DTO;

import java.util.UUID;

public class AlumnoTutorDTO {

    private UUID id;
    private UUID alumnoId;
    private UUID tutorId;
    private UUID cicloId;

    // Constructores

    public AlumnoTutorDTO() {}

    public AlumnoTutorDTO(UUID id, UUID alumnoId, UUID tutorId, UUID cicloId) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.tutorId = tutorId;
        this.cicloId = cicloId;
    }

    // Getters y setters

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
}