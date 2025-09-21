package com.primaria.app.Model;


import jakarta.persistence.Entity;

@Entity
public class Director extends Usuario {

    private String departamento;

    private String telefono;
    
    // Getters y Setters

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public String gettelefono() {
        return telefono;
    }

    public void settelefono(String telefono) {
        this.telefono = telefono;
    }
    
}