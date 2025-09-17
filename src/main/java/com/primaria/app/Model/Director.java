package com.primaria.app.Model;


import jakarta.persistence.Entity;

@Entity
public class Director extends Usuario {

    private String departamento;

    // Getters y Setters

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}