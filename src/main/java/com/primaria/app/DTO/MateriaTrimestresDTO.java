package com.primaria.app.DTO;

import java.util.LinkedHashMap;
import java.util.Map;

public class MateriaTrimestresDTO {
    private String nombreAlumno;
    
    private String nombreMateria;
    private Map<String, Double> calificacionesPorTrimestre = new LinkedHashMap<>();

    public MateriaTrimestresDTO(String nombreAlumno,  String nombreMateria) {
        this.nombreAlumno = nombreAlumno;
     
        this.nombreMateria = nombreMateria;
    }

    public void agregarCalificacion(String nombreTrimestre, Double calificacion) {
        calificacionesPorTrimestre.put(nombreTrimestre, calificacion);
    }

    // Getters
    public String getNombreAlumno() { return nombreAlumno; }
   
    public String getNombreMateria() { return nombreMateria; }
    public Map<String, Double> getCalificacionesPorTrimestre() { return calificacionesPorTrimestre; }
}
