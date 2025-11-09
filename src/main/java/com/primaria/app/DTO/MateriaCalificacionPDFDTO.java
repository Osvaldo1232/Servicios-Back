package com.primaria.app.DTO;

import java.util.List;

public class MateriaCalificacionPDFDTO {
    private String nombreMateria;
    private List<TrimestreCalificacionDTO> trimestres;
    private Double calificacionFinal;

    public MateriaCalificacionPDFDTO(String nombreMateria, List<TrimestreCalificacionDTO> trimestres, Double calificacionFinal) {
        this.nombreMateria = nombreMateria;
        this.trimestres = trimestres;
        this.calificacionFinal = calificacionFinal;
    }

    // Getters y setters
    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public List<TrimestreCalificacionDTO> getTrimestres() { return trimestres; }
    public void setTrimestres(List<TrimestreCalificacionDTO> trimestres) { this.trimestres = trimestres; }

    public Double getCalificacionFinal() { return calificacionFinal; }
    public void setCalificacionFinal(Double calificacionFinal) { this.calificacionFinal = calificacionFinal; }
}
