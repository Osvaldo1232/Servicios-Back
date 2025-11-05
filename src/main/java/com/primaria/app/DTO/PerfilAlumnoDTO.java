package com.primaria.app.DTO;

public class PerfilAlumnoDTO {

    private String curp;
    private String matricula;
    private String nombreAlumno;
    private String nombreTutor;
    private String parentesco;
    private String grado;
    private String grupo;
    private String ciclo;

    public PerfilAlumnoDTO(String curp, String matricula, String nombreAlumno,
                           String nombreTutor, String parentesco,
                           String grado, String grupo, String ciclo) {
        this.curp = curp;
        this.matricula = matricula;
        this.nombreAlumno = nombreAlumno;
        this.nombreTutor = nombreTutor;
        this.parentesco = parentesco;
        this.grado = grado;
        this.grupo = grupo;
        this.ciclo = ciclo;
    }

    // Getters y setters
    public String getCurp() { return curp; }
    public String getMatricula() { return matricula; }
    public String getNombreAlumno() { return nombreAlumno; }
    public String getNombreTutor() { return nombreTutor; }
    public String getParentesco() { return parentesco; }
    public String getGrado() { return grado; }
    public String getGrupo() { return grupo; }
    public String getCiclo() { return ciclo; }
}
