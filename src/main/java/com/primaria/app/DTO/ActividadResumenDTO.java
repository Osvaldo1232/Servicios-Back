package com.primaria.app.DTO;

import java.time.LocalDate;

public class ActividadResumenDTO {

    private String idActividad;
    private String nombreActividad;
    private LocalDate fecha;
    private Double valor;

    private String idDocente;
    private String nombreDocente;

    private String nombreGrado;
    private String nombreMateria;

    // Constructor
    public ActividadResumenDTO(String idActividad, String nombreActividad, LocalDate fecha, Double valor,
                               String idDocente, String nombreDocente,
                               String nombreGrado, String nombreMateria) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.fecha = fecha;
        this.valor = valor;
        this.idDocente = idDocente;
        this.nombreDocente = nombreDocente;
        this.nombreGrado = nombreGrado;
        this.nombreMateria = nombreMateria;
    }

    // Getters y setters
    public String getIdActividad() { return idActividad; }
    public void setIdActividad(String idActividad) { this.idActividad = idActividad; }

    public String getNombreActividad() { return nombreActividad; }
    public void setNombreActividad(String nombreActividad) { this.nombreActividad = nombreActividad; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getIdDocente() { return idDocente; }
    public void setIdDocente(String idDocente) { this.idDocente = idDocente; }

    public String getNombreDocente() { return nombreDocente; }
    public void setNombreDocente(String nombreDocente) { this.nombreDocente = nombreDocente; }

    public String getNombreGrado() { return nombreGrado; }
    public void setNombreGrado(String nombreGrado) { this.nombreGrado = nombreGrado; }

    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }
}
