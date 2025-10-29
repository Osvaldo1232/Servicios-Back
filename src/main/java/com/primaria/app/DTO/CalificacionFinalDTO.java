package com.primaria.app.DTO;

public class CalificacionFinalDTO {

    private String id;
    private String idAlumno;
    private String idMateria;
    private String idTrimestre;
    private String idCicloEscolar;
    private String idGrado;
    private Double promedio;

    public CalificacionFinalDTO() {}

    public CalificacionFinalDTO(String id, String idAlumno, String idMateria, String idTrimestre, String idCicloEscolar, String idGrado, Double promedio) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.idMateria = idMateria;
        this.idTrimestre = idTrimestre;
        this.idCicloEscolar = idCicloEscolar;
        this.idGrado = idGrado;
        this.promedio = promedio;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdAlumno() { return idAlumno; }
    public void setIdAlumno(String idAlumno) { this.idAlumno = idAlumno; }

    public String getIdMateria() { return idMateria; }
    public void setIdMateria(String idMateria) { this.idMateria = idMateria; }

    public String getIdTrimestre() { return idTrimestre; }
    public void setIdTrimestre(String idTrimestre) { this.idTrimestre = idTrimestre; }

    public String getIdCicloEscolar() { return idCicloEscolar; }
    public void setIdCicloEscolar(String idCicloEscolar) { this.idCicloEscolar = idCicloEscolar; }

    public String getIdGrado() { return idGrado; }
    public void setIdGrado(String idGrado) { this.idGrado = idGrado; }

    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
}
