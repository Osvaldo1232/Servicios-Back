package com.primaria.app.DTO;

public class Tipos_EvaluacionResumenDTO {
    private String id;
    private String nombre;

    public Tipos_EvaluacionResumenDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}