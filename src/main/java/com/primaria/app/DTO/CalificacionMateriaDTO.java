package com.primaria.app.DTO;

import java.math.BigDecimal;

public class CalificacionMateriaDTO {

    private String idGrado;
    private String nombreGrado;

    private String idMateria;
    private String nombreMateria;

    private String idCampoFormativo;
    private String nombreCampoFormativo;

    private BigDecimal promedio; // Calificación de la materia

    public CalificacionMateriaDTO(
            String idGrado, String nombreGrado,
            String idMateria, String nombreMateria,
            String idCampoFormativo, String nombreCampoFormativo,
            BigDecimal promedio
    ) {
        this.idGrado = idGrado;
        this.nombreGrado = nombreGrado;
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.idCampoFormativo = idCampoFormativo;
        this.nombreCampoFormativo = nombreCampoFormativo;
        
        
        this.promedio = promedio != null 
        	    ? promedio.setScale(1, BigDecimal.ROUND_HALF_UP) 
        	    : null;

    }

    // Getters y setters
    public String getIdGrado() { return idGrado; }
    public String getNombreGrado() { return nombreGrado; }
    public String getIdMateria() { return idMateria; }
    public String getNombreMateria() { return nombreMateria; }
    public String getIdCampoFormativo() { return idCampoFormativo; }
    public String getNombreCampoFormativo() { return nombreCampoFormativo; }
    public BigDecimal getPromedio() { return promedio; }

    
    
    public void setPromedio(BigDecimal promedio) { this.promedio = promedio; }
}
