package com.primaria.app.DTO;

import java.time.LocalDate;
import com.primaria.app.Model.Estatus;

public class InscritoAlumnoDTO {

    private String id;
    private String alumnoId;
    private String docenteId;
    private String gradoId;
    private String grupoId;
    private String cicloId;
    private Estatus estatus;
    private LocalDate fechaInscripcion;

    // Constructors
    public InscritoAlumnoDTO() {}

    public InscritoAlumnoDTO(String id, String alumnoId, String docenteId, String gradoId, String grupoId, String cicloId) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.docenteId = docenteId;
        this.gradoId = gradoId;
        this.grupoId = grupoId;
        this.cicloId = cicloId;
    }

    // Getters y Setters
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

    public String getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(String docenteId) {
        this.docenteId = docenteId;
    }

    public String getGradoId() {
        return gradoId;
    }

    public void setGradoId(String gradoId) {
        this.gradoId = gradoId;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getCicloId() {
        return cicloId;
    }

    public void setCicloId(String cicloId) {
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
