package com.primaria.app.DTO;

import java.math.BigDecimal;

public class MateriaReprobadaDTO {

    private String nombreMateria;
    private BigDecimal promedio;

    public MateriaReprobadaDTO(String nombreMateria, BigDecimal promedio) {
        this.nombreMateria = nombreMateria;
        
        
        this.promedio = promedio;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }
}
