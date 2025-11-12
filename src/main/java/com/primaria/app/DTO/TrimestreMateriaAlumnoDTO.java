package com.primaria.app.DTO;

public class TrimestreMateriaAlumnoDTO {

    private String nombreAlumno;
    private String nombreGrado;
    private String nombreMateria;
    private Double trimestre1;
    private Double trimestre2;
    private Double trimestre3;
    private Double promedioFinal;

    public TrimestreMateriaAlumnoDTO(String nombreAlumno, String nombreGrado, String nombreMateria,
                                     Double trimestre1, Double trimestre2, Double trimestre3, Double promedioFinal) {
        this.nombreAlumno = nombreAlumno;
        this.nombreGrado = nombreGrado;
        this.nombreMateria = nombreMateria;
        this.trimestre1 = trimestre1;
        this.trimestre2 = trimestre2;
        this.trimestre3 = trimestre3;
        this.promedioFinal = promedioFinal;
    }

    // Getters y setters
    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }

    public String getNombreGrado() { return nombreGrado; }
    public void setNombreGrado(String nombreGrado) { this.nombreGrado = nombreGrado; }

    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public Double getTrimestre1() { return trimestre1; }
    public void setTrimestre1(Double trimestre1) { this.trimestre1 = trimestre1; }

    public Double getTrimestre2() { return trimestre2; }
    public void setTrimestre2(Double trimestre2) { this.trimestre2 = trimestre2; }

    public Double getTrimestre3() { return trimestre3; }
    public void setTrimestre3(Double trimestre3) { this.trimestre3 = trimestre3; }

    public Double getPromedioFinal() { return promedioFinal; }
    public void setPromedioFinal(Double promedioFinal) { this.promedioFinal = promedioFinal; }
}
