package com.primaria.app.DTO;

import java.time.LocalDateTime;
import com.primaria.app.Model.Estatus;

public class InscritoAlumnoDTO {

    private String id;
    private String alumnoId;
    private String asignacionId;
    private Estatus estatus;
    private LocalDateTime fechaInscripcion;

    // --- Constructores ---
    public InscritoAlumnoDTO() {}

    public InscritoAlumnoDTO(String id, String alumnoId, String asignacionId, Estatus estatus, LocalDateTime fechaInscripcion) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.asignacionId = asignacionId;
        this.estatus = estatus;
        this.fechaInscripcion = fechaInscripcion;
    }

    // --- Getters y Setters ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getAsignacionId() {
        return asignacionId;
    }

    public void setAsignacionId(String asignacionId) {
        this.asignacionId = asignacionId;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
