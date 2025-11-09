package com.primaria.app.DTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PromedioGradoCicloDTO {

    private String idGrado;
    private String nombreGrado;
    private String idCiclo;
    private String cicloEscolar;
    private BigDecimal promedioGeneral;

    public PromedioGradoCicloDTO() {}

    public PromedioGradoCicloDTO(String idGrado, String nombreGrado, String idCiclo, String cicloEscolar, BigDecimal promedioGeneral) {
        this.idGrado = idGrado;
        this.nombreGrado = nombreGrado;
        this.idCiclo = idCiclo;
        this.cicloEscolar = cicloEscolar;
        // ✅ redondeamos a 2 decimales
        this.promedioGeneral = promedioGeneral.setScale(2, RoundingMode.HALF_UP);
    }

    // Getters y Setters
    public String getIdGrado() { return idGrado; }
    public void setIdGrado(String idGrado) { this.idGrado = idGrado; }

    public String getNombreGrado() { return nombreGrado; }
    public void setNombreGrado(String nombreGrado) { this.nombreGrado = nombreGrado; }

    public String getIdCiclo() { return idCiclo; }
    public void setIdCiclo(String idCiclo) { this.idCiclo = idCiclo; }

    public String getCicloEscolar() { return cicloEscolar; }
    public void setCicloEscolar(String cicloEscolar) { this.cicloEscolar = cicloEscolar; }

    public BigDecimal getPromedioGeneral() { 
        return promedioGeneral != null ? promedioGeneral.setScale(2, RoundingMode.HALF_UP) : null;
    }
    public void setPromedioGeneral(BigDecimal promedioGeneral) {
        this.promedioGeneral = promedioGeneral != null ? promedioGeneral.setScale(2, RoundingMode.HALF_UP) : null;
    }
}
