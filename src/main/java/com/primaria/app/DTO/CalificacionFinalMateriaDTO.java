package com.primaria.app.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CalificacionFinalMateriaDTO {

    private String id;
    private String alumnoId;
    private String materiaId;
    private String cicloEscolarId;
    private BigDecimal promedio;
    private LocalDateTime fechaCreacion;
    private String gradoId;
    // Constructor vacío
    public CalificacionFinalMateriaDTO() {}

    // Constructor completo
    public CalificacionFinalMateriaDTO(String id, String alumnoId, String materiaId, String cicloEscolarId, String gradoId, BigDecimal promedio, LocalDateTime fechaCreacion) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.materiaId = materiaId;
        this.cicloEscolarId = cicloEscolarId;
        this.gradoId = gradoId;
        this.promedio = promedio;
        this.fechaCreacion = fechaCreacion;
    }


    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAlumnoId() { return alumnoId; }
    public void setAlumnoId(String alumnoId) { this.alumnoId = alumnoId; }

    public String getMateriaId() { return materiaId; }
    public void setMateriaId(String materiaId) { this.materiaId = materiaId; }

    public String getCicloEscolarId() { return cicloEscolarId; }
    public void setCicloEscolarId(String cicloEscolarId) { this.cicloEscolarId = cicloEscolarId; }

    public BigDecimal getPromedio() { return promedio; }
    public void setPromedio(BigDecimal promedio) { this.promedio = promedio; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public String getGradoId() { return gradoId; }
    public void setGradoId(String gradoId) { this.gradoId = gradoId; }
}
