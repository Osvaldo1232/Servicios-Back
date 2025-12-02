package com.primaria.app.DTO;

import com.primaria.app.Model.Estatus;

public class AsignacionMateriaGradoResumeDTO {

    private String idGrado;
    private String nombreGrado;

    private String idMateria;
    private String nombreMateria;

    private String idCampoFormativo;
    private String nombreCampoFormativo;

    private Estatus estatusMateria; // 🔥 Nuevo campo

    // 🔥 Constructor completo
    public AsignacionMateriaGradoResumeDTO(
            String idGrado,
            String nombreGrado,
            String idMateria,
            String nombreMateria,
            String idCampoFormativo,
            String nombreCampoFormativo,
            Estatus estatusMateria
    ) {
        this.idGrado = idGrado;
        this.nombreGrado = nombreGrado;
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.idCampoFormativo = idCampoFormativo;
        this.nombreCampoFormativo = nombreCampoFormativo;
        this.estatusMateria = estatusMateria;
    }

    // Getters y Setters
    public String getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getIdCampoFormativo() {
        return idCampoFormativo;
    }

    public void setIdCampoFormativo(String idCampoFormativo) {
        this.idCampoFormativo = idCampoFormativo;
    }

    public String getNombreCampoFormativo() {
        return nombreCampoFormativo;
    }

    public void setNombreCampoFormativo(String nombreCampoFormativo) {
        this.nombreCampoFormativo = nombreCampoFormativo;
    }

    public Estatus getEstatusMateria() {
        return estatusMateria;
    }

    public void setEstatusMateria(Estatus estatusMateria) {
        this.estatusMateria = estatusMateria;
    }
}
