package com.primaria.app.DTO;

public class MateriaCalificacionDTO {
    private String nombreMateria;
    private Double calificacion; // puede ser null si aún no hay calificación

    public MateriaCalificacionDTO(String nombreMateria, Double calificacion) {
        this.nombreMateria = nombreMateria;
        this.calificacion = calificacion;
    }

    // Getters y setters
    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public Double getCalificacion() { return calificacion; }
    public void setCalificacion(Double calificacion) { this.calificacion = calificacion; }
}
