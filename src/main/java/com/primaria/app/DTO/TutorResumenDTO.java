package com.primaria.app.DTO;

public class TutorResumenDTO {
    private String id;
    private String nombre;

    public TutorResumenDTO(String id, String nombre) {
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