package com.primaria.app.Model;


import jakarta.persistence.Entity;

@Entity
public class Administrador extends Usuario {

    private String departamento;
    private String telefono;

    // Getters y Setters
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
