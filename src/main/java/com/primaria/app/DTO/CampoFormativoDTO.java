package com.primaria.app.DTO;
import java.util.UUID;

import com.primaria.app.Model.Estatus;

public class CampoFormativoDTO {

    private UUID id;
    private String nombre;

    private Estatus estatus;
    // Constructores
    public CampoFormativoDTO() {}

    public CampoFormativoDTO(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
       
    }

    // Getters y setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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