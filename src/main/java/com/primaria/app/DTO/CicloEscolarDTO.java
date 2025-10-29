package com.primaria.app.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.primaria.app.Model.Estatus;

public class CicloEscolarDTO {

    private UUID id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Estatus estatus;
    private LocalDateTime fechaCreado; // Nuevo campo

    // Constructor vacío
    public CicloEscolarDTO() {}

    // Constructor con argumentos
    public CicloEscolarDTO(UUID id, LocalDate fechaInicio, LocalDate fechaFin, Estatus estatus, LocalDateTime fechaCreado) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estatus = estatus;
        this.fechaCreado = fechaCreado;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(LocalDateTime fechaCreado) {
        this.fechaCreado = fechaCreado;
    }
}
