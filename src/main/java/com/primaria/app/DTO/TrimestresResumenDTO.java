package com.primaria.app.DTO;

public class TrimestresResumenDTO {
    private String id;
    private String nombre;

    public TrimestresResumenDTO(String id, String nombre) {
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