package com.primaria.app.DTO;

import java.util.List;

public class GradoCalificacionDTO {

    private String gradoNombre;
    private String alumnoId;
    private String nombreAlumno;
    private List<MateriaCalificacionPDFDTO> materias;
    private Double promedioGrado;

    public GradoCalificacionDTO() {} // constructor vacío

    // ✅ Constructor que necesitamos
    public GradoCalificacionDTO(String gradoNombre, String alumnoId, String nombreAlumno,
                                List<MateriaCalificacionPDFDTO> materias, Double promedioGrado) {
        this.gradoNombre = gradoNombre;
        this.alumnoId = alumnoId;
        this.nombreAlumno = nombreAlumno;
        this.materias = materias;
        this.promedioGrado = promedioGrado;
    }

    // Getters y Setters
    public String getGradoNombre() { return gradoNombre; }
    public void setGradoNombre(String gradoNombre) { this.gradoNombre = gradoNombre; }

    public String getAlumnoId() { return alumnoId; }
    public void setAlumnoId(String alumnoId) { this.alumnoId = alumnoId; }

    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }

    public List<MateriaCalificacionPDFDTO> getMaterias() { return materias; }
    public void setMaterias(List<MateriaCalificacionPDFDTO> materias) { this.materias = materias; }

    public Double getPromedioGrado() { return promedioGrado; }
    public void setPromedioGrado(Double promedioGrado) { this.promedioGrado = promedioGrado; }
}
