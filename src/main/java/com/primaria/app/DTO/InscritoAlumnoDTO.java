package com.primaria.app.DTO;

import java.time.LocalDate;
import java.util.UUID;

import com.primaria.app.Model.Estatus;

public class InscritoAlumnoDTO {

    private UUID id;

    private UUID alumnoId;
    private UUID docenteId;
    private UUID gradoId;
    private UUID grupoId;
    private UUID cicloId;
    private Estatus estatus;
    
    private LocalDate fechaInscripcion;
    // Constructors

    public InscritoAlumnoDTO() {}

    public InscritoAlumnoDTO(UUID id, UUID alumnoId, UUID docenteId, UUID gradoId, UUID grupoId, UUID cicloId) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.docenteId = docenteId;
        this.gradoId = gradoId;
        this.grupoId = grupoId;
        this.cicloId = cicloId;
    }

    // Getters & Setters

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

    public UUID getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(UUID docenteId) {
        this.docenteId = docenteId;
    }

    public UUID getGradoId() {
        return gradoId;
    }

    public void setGradoId(UUID gradoId) {
        this.gradoId = gradoId;
    }

    public UUID getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(UUID grupoId) {
        this.grupoId = grupoId;
    }

    public UUID getCicloId() {
        return cicloId;
    }

    public void setCicloId(UUID cicloId) {
        this.cicloId = cicloId;
    }
    

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
    
    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}