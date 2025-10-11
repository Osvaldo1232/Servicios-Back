package com.primaria.app.DTO;

import java.time.LocalDate;

public class ActividadDTO {

    private String id;
    private String nombre;
    private LocalDate fecha;
    private Double valor;

    // IDs de relaciones
    private String docenteId;
    private String asignacionMateriaGradoId;
    private String trimestreId;
    private String tipoEvaluacionId;

    // Constructores
    public ActividadDTO() {}

    public ActividadDTO(String id, String nombre, LocalDate fecha, Double valor,
                        String docenteId, String asignacionMateriaGradoId,
                        String trimestreId, String tipoEvaluacionId) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.valor = valor;
        this.docenteId = docenteId;
        this.asignacionMateriaGradoId = asignacionMateriaGradoId;
        this.trimestreId = trimestreId;
        this.tipoEvaluacionId = tipoEvaluacionId;
    }

    // GETTERS Y SETTERS
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public String getDocenteId() {
        return docenteId;
    }
    public void setDocenteId(String docenteId) {
        this.docenteId = docenteId;
    }

    public String getAsignacionMateriaGradoId() {
        return asignacionMateriaGradoId;
    }
    public void setAsignacionMateriaGradoId(String asignacionMateriaGradoId) {
        this.asignacionMateriaGradoId = asignacionMateriaGradoId;
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
}
