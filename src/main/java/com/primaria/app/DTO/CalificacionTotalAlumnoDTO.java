package com.primaria.app.DTO;

import java.math.BigDecimal;

public class CalificacionTotalAlumnoDTO {
    private String idAlumno;
    private String nombreAlumno;
    private String grado;
    private String grupo;
    private String ciclo;
    private BigDecimal calificacionTotal;

    public CalificacionTotalAlumnoDTO(String idAlumno, String nombreAlumno, String grado, String grupo, String ciclo, BigDecimal calificacionTotal) {
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.grado = grado;
        this.grupo = grupo;
        this.ciclo = ciclo;
        this.calificacionTotal = calificacionTotal;
    }

    // Getters y setters
    public String getIdAlumno() { return idAlumno; }
    public void setIdAlumno(String idAlumno) { this.idAlumno = idAlumno; }
    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }
    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }
    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
    public String getCiclo() { return ciclo; }
    public void setCiclo(String ciclo) { this.ciclo = ciclo; }
    public BigDecimal getCalificacionTotal() { return calificacionTotal; }
    public void setCalificacionTotal(BigDecimal calificacionTotal) { this.calificacionTotal = calificacionTotal; }
}
