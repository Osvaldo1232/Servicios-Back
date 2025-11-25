package com.primaria.app.DTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MateriasCalificacionDTO {

    private String nombreMateria;
    private BigDecimal calificacion;

    public MateriasCalificacionDTO(String nombreMateria, BigDecimal calificacion) {
        this.nombreMateria = nombreMateria;
        
        this.calificacion = calificacion.setScale(1, RoundingMode.HALF_UP);
       
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }
}
