


package com.primaria.app.DTO;

public class MateriaCalificacionResDTO {
    private String nombreMateria;
    private String nombreCampoFormativo;
   
    private Double calificacionActual;

    public MateriaCalificacionResDTO(String nombreMateria, String nombreCampoFormativo,  Double calificacionActual) {
        this.nombreMateria = nombreMateria;
        this.nombreCampoFormativo = nombreCampoFormativo;
       
        this.calificacionActual = calificacionActual;
    }

    // Getters
    public String getNombreMateria() { return nombreMateria; }
    public String getNombreCampoFormativo() { return nombreCampoFormativo; }
 
    public Double getCalificacionActual() { return calificacionActual; }
}
