package com.primaria.app.Model;

import jakarta.persistence.Entity;

@Entity
public class Profesor extends Usuario {

    private String especialidad;

    // Getters y Setters

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}