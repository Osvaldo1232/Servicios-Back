package com.primaria.app.DTO;

public class InfoAlumnoTutorDTO {

    private String curp;
    private String nombreAlumno;
    private String nombreTutor;
    private String matricula;
    private String grado;
    private String grupo;
    private String ciclo;

    // Constructor vacío
    public InfoAlumnoTutorDTO() {}

    // Constructor con datos
    public InfoAlumnoTutorDTO(String curp, String nombreAlumno, String nombreTutor,
                              String matricula, String grado, String grupo, String ciclo) {
        this.curp = curp;
        this.nombreAlumno = nombreAlumno;
        this.nombreTutor = nombreTutor;
        this.matricula = matricula;
        this.grado = grado;
        this.grupo = grupo;
        this.ciclo = ciclo;
    }

    // Getters y Setters
    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }

    public String getNombreTutor() { return nombreTutor; }
    public void setNombreTutor(String nombreTutor) { this.nombreTutor = nombreTutor; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getCiclo() { return ciclo; }
    public void setCiclo(String ciclo) { this.ciclo = ciclo; }
}
