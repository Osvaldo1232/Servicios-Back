package com.primaria.app.DTO;

public class MateriaCalificacionPDFDTO {
    private String nombreMateria;
    private Double trimestre1;
    private Double trimestre2;
    private Double trimestre3;
    private Double calificacionFinal;

    public MateriaCalificacionPDFDTO(String nombreMateria, Double trimestre1, Double trimestre2, Double trimestre3, Double calificacionFinal) {
        this.nombreMateria = nombreMateria;
        this.trimestre1 = trimestre1;
        this.trimestre2 = trimestre2;
        this.trimestre3 = trimestre3;
        this.calificacionFinal = calificacionFinal;
    }

    // Getters y setters
    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public Double getTrimestre1() { return trimestre1; }
    public void setTrimestre1(Double trimestre1) { this.trimestre1 = trimestre1; }

    public Double getTrimestre2() { return trimestre2; }
    public void setTrimestre2(Double trimestre2) { this.trimestre2 = trimestre2; }

    public Double getTrimestre3() { return trimestre3; }
    public void setTrimestre3(Double trimestre3) { this.trimestre3 = trimestre3; }

    public Double getCalificacionFinal() { return calificacionFinal; }
    public void setCalificacionFinal(Double calificacionFinal) { this.calificacionFinal = calificacionFinal; }
}
