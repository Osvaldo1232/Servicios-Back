package com.primaria.app.DTO;

import java.time.LocalDate;
import java.util.UUID;

public class ActividadDTO {

    private UUID id;

    private String docenteId;
    private String alumnoId;
    private String materiaId;
    private String gradoId;
    private String grupoId;
    private String trimestreId;
    private String tipoEvaluacionId;
    private String cicloId; 
    private String nombre;
    private LocalDate fecha;
    private Double valor;

    // Constructores
    public ActividadDTO() {}

    public ActividadDTO(UUID id, String docenteId, String alumnoId, String materiaId, String gradoId, String grupoId, String trimestreId, String tipoEvaluacionId, String nombre, LocalDate fecha, Double valor) {
        this.id = id;
        this.docenteId = docenteId;
        this.alumnoId = alumnoId;
        this.materiaId = materiaId;
        this.gradoId = gradoId;
        this.grupoId = grupoId;
        this.trimestreId = trimestreId;
        this.tipoEvaluacionId = tipoEvaluacionId;
        this.nombre = nombre;
        this.fecha = fecha;
        this.valor = valor;
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(String docenteId) {
        this.docenteId = docenteId;
    }

    public String getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(String materiaId) {
        this.materiaId = materiaId;
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

    public String getTrimestreId() {
        return trimestreId;
    }

    public void setTrimestreId(String trimestreId) {
        this.trimestreId = trimestreId;
    }

    public String getTipoEvaluacionId() {
        return tipoEvaluacionId;
    }

    public void setTipoEvaluacionId(String tipoEvaluacionId) {
        this.tipoEvaluacionId = tipoEvaluacionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    public String getCicloId() {
        return cicloId;
    }

    public void setCicloId(String cicloId) {
        this.cicloId = cicloId;
    }
    
}
