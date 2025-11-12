package com.primaria.app.DTO;

import java.math.BigDecimal;

public class MateriaCalificacionResDTO {
    private String idMateria;
    private String nombreMateria;
    private BigDecimal calificacionActual;
    private String idGrado;
    private String nombreGrado;
    private String nombreCampoFormativo; // ✅ nuevo campo

    public MateriaCalificacionResDTO(String idMateria, String nombreMateria, BigDecimal calificacionActual,
                                     String idGrado, String nombreGrado, String nombreCampoFormativo) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.calificacionActual = calificacionActual;
        this.idGrado = idGrado;
        this.nombreGrado = nombreGrado;
        this.nombreCampoFormativo = nombreCampoFormativo;
    }

    // Getters
    public String getIdMateria() { return idMateria; }
    public String getNombreMateria() { return nombreMateria; }
    public BigDecimal getCalificacionActual() { return calificacionActual; }
    public String getIdGrado() { return idGrado; }
    public String getNombreGrado() { return nombreGrado; }
    public String getNombreCampoFormativo() { return nombreCampoFormativo; }
}
