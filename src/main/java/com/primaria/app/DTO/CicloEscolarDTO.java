package com.primaria.app.DTO;

import java.time.LocalDateTime;
import java.util.UUID;
import com.primaria.app.Model.Estatus;

public class CicloEscolarDTO {

    private String id;
    private int anioInicio;
    private int anioFin;
    private Estatus estatus;
    private LocalDateTime fechaCreado;

    // Constructor vacío
    public CicloEscolarDTO() {}

    // Constructor con argumentos
    public CicloEscolarDTO(String id, int anioInicio, int anioFin, Estatus estatus, LocalDateTime fechaCreado) {
        this.id = id;
        this.anioInicio = anioInicio;
        this.anioFin = anioFin;
        this.estatus = estatus;
        this.fechaCreado = fechaCreado;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAnioInicio() {
        return anioInicio;
    }

    public void setAnioInicio(int anioInicio) {
        this.anioInicio = anioInicio;
    }

    public int getAnioFin() {
        return anioFin;
    }

    public void setAnioFin(int anioFin) {
        this.anioFin = anioFin;
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
