package com.primaria.app.DTO;

import java.util.UUID;

import com.primaria.app.Model.Estatus;
public class AsignacionMateriaGradoDTO {

    private String idMateria;
    private String idGrado;

    // Getters y Setters
    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }
}
