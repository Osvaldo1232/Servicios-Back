package com.primaria.app.DTO;



public class CalificacionAlumnoDTO {

    private String nombreMateria;
    private String idAlumno;
    private String nombreAlumno;

    private String idTrimestre1;
    private Double trimestre1;

    private String idTrimestre2;
    private Double trimestre2;

    private String idTrimestre3;
    private Double trimestre3;

    private Double promedioFinal;

    public CalificacionAlumnoDTO(
            String nombreMateria, String idAlumno, String nombreAlumno,
            String idTrimestre1, Double trimestre1,
            String idTrimestre2, Double trimestre2,
            String idTrimestre3, Double trimestre3,
            Double promedioFinal) {

        this.nombreMateria = nombreMateria;
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.idTrimestre1 = idTrimestre1;
        this.trimestre1 = trimestre1;
        this.idTrimestre2 = idTrimestre2;
        this.trimestre2 = trimestre2;
        this.idTrimestre3 = idTrimestre3;
        this.trimestre3 = trimestre3;
        this.promedioFinal = promedioFinal;
    }

    // Getters y setters
    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public String getIdAlumno() { return idAlumno; }
    public void setIdAlumno(String idAlumno) { this.idAlumno = idAlumno; }

    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }

    public String getIdTrimestre1() { return idTrimestre1; }
    public void setIdTrimestre1(String idTrimestre1) { this.idTrimestre1 = idTrimestre1; }

    public Double getTrimestre1() { return trimestre1; }
    public void setTrimestre1(Double trimestre1) { this.trimestre1 = trimestre1; }

    public String getIdTrimestre2() { return idTrimestre2; }
    public void setIdTrimestre2(String idTrimestre2) { this.idTrimestre2 = idTrimestre2; }

    public Double getTrimestre2() { return trimestre2; }
    public void setTrimestre2(Double trimestre2) { this.trimestre2 = trimestre2; }

    public String getIdTrimestre3() { return idTrimestre3; }
    public void setIdTrimestre3(String idTrimestre3) { this.idTrimestre3 = idTrimestre3; }

    public Double getTrimestre3() { return trimestre3; }
    public void setTrimestre3(Double trimestre3) { this.trimestre3 = trimestre3; }

    public Double getPromedioFinal() { return promedioFinal; }
    public void setPromedioFinal(Double promedioFinal) { this.promedioFinal = promedioFinal; }
}

