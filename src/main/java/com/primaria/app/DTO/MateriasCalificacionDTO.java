package com.primaria.app.DTO;

import java.math.BigDecimal;

public class MateriasCalificacionDTO {

    private String nombreMateria;
    private BigDecimal calificacion;

    public MateriasCalificacionDTO(String nombreMateria, BigDecimal calificacion) {
        this.nombreMateria = nombreMateria;
        this.calificacion = calificacion;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }
}
