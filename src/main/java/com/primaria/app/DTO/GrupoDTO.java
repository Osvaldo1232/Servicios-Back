package com.primaria.app.DTO;

import java.util.UUID;

import com.primaria.app.Model.Estatus;

public class GrupoDTO {

    private String id;
    private String nombre;

    private Estatus estatus;
    public GrupoDTO() {}

   
    public GrupoDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
}
