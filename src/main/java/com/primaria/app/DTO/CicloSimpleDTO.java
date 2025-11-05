package com.primaria.app.DTO;

public class CicloSimpleDTO {
    private String id;
    private String nombre;

    public CicloSimpleDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
}
