package com.primaria.app.DTO;

public class TrimestreCalificacionDTO {
    private String nombre;
    private Double promedio;

    public TrimestreCalificacionDTO(String nombre, Double promedio) {
        this.nombre = nombre;
        this.promedio = promedio;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
}
