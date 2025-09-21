package com.primaria.app.DTO;

import com.primaria.app.Model.Estatus;

public class ProfesorDTO extends UsuarioDTO {

    private String especialidad;
    private Estatus estatus; 
    // Getters y Setters

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
}