package com.primaria.app.DTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PromedioCampoFormativoDTO {

    private String nombreCampoFormativo;
    private BigDecimal promedioTrimestre1;
    private BigDecimal promedioTrimestre2;
    private BigDecimal promedioTrimestre3;
    private BigDecimal promedioFinal;

    public PromedioCampoFormativoDTO(String nombreCampoFormativo,
            Double promedioTrimestre1,
            Double promedioTrimestre2,
            Double promedioTrimestre3,
            Double promedioFinal) {

this.nombreCampoFormativo = nombreCampoFormativo;

this.promedioTrimestre1 = BigDecimal
.valueOf(promedioTrimestre1 == null ? 0 : promedioTrimestre1)
.setScale(1, RoundingMode.HALF_UP);

this.promedioTrimestre2 = BigDecimal
.valueOf(promedioTrimestre2 == null ? 0 : promedioTrimestre2)
.setScale(1, RoundingMode.HALF_UP);

this.promedioTrimestre3 = BigDecimal
.valueOf(promedioTrimestre3 == null ? 0 : promedioTrimestre3)
.setScale(1, RoundingMode.HALF_UP);

this.promedioFinal = BigDecimal
.valueOf(promedioFinal == null ? 0 : promedioFinal)
.setScale(1, RoundingMode.HALF_UP);
}


    // Getters
    public String getNombreCampoFormativo() { return nombreCampoFormativo; }
    public BigDecimal getPromedioTrimestre1() { return promedioTrimestre1; }
    public BigDecimal getPromedioTrimestre2() { return promedioTrimestre2; }
    public BigDecimal getPromedioTrimestre3() { return promedioTrimestre3; }
    public BigDecimal getPromedioFinal() { return promedioFinal; }
}
