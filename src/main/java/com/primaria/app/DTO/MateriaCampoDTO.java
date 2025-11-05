package com.primaria.app.DTO;


import java.util.UUID;
import com.primaria.app.Model.Estatus;

public class MateriaCampoDTO {

    private String id;
    private String nombre;
    private Estatus estatus;
    private String campoFormativoId;
    private String campoFormativoNombre;

    public MateriaCampoDTO() {}

    public MateriaCampoDTO(String id, String nombre, Estatus estatus, String campoFormativoId, String campoFormativoNombre) {
        this.id = id;
        this.nombre = nombre;
        this.estatus = estatus;
        this.campoFormativoId = campoFormativoId;
        this.campoFormativoNombre = campoFormativoNombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Estatus getEstatus() { return estatus; }
    public void setEstatus(Estatus estatus) { this.estatus = estatus; }

    public String getCampoFormativoId() { return campoFormativoId; }
    public void setCampoFormativoId(String campoFormativoId) { this.campoFormativoId = campoFormativoId; }

    public String getCampoFormativoNombre() { return campoFormativoNombre; }
    public void setCampoFormativoNombre(String campoFormativoNombre) { this.campoFormativoNombre = campoFormativoNombre; }
}
