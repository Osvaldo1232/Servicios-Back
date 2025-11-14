package com.primaria.app.DTO;

import java.math.BigDecimal;

public class PromedioAlumnoDTO {

    private String idAlumno;
    private String nombreCompleto;
    private BigDecimal promedioGeneral;

    public PromedioAlumnoDTO(String idAlumno, String nombreCompleto, BigDecimal promedioGeneral) {
        this.idAlumno = idAlumno;
        this.nombreCompleto = nombreCompleto;
        this.promedioGeneral = promedioGeneral;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public BigDecimal getPromedioGeneral() {
        return promedioGeneral;
    }

    public void setPromedioGeneral(BigDecimal promedioGeneral) {
        this.promedioGeneral = promedioGeneral;
    }

}
