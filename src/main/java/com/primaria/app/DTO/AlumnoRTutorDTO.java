package com.primaria.app.DTO;

public class AlumnoRTutorDTO {

    private String curp;
    private String nombreTutor;
    private String nombreAlumno;
    private String matricula;
    private String grado;
    private String grupo;

    
    public AlumnoRTutorDTO(String curp, String nombreTutor, String nombreAlumno,
                          String matricula, String grado, String grupo) {
        this.curp = curp;
        this.nombreTutor = nombreTutor;
        this.nombreAlumno = nombreAlumno;
        this.matricula = matricula;
        this.grado = grado;
        this.grupo = grupo;
    }

    // 🔹 Constructor vacío (por si se necesita para serialización JSON)
    public AlumnoRTutorDTO() {
    }

    // 🔹 Getters y Setters
    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombreTutor() {
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "AlumnoTutorDTO{" +
                "curp='" + curp + '\'' +
                ", nombreTutor='" + nombreTutor + '\'' +
                ", nombreAlumno='" + nombreAlumno + '\'' +
                ", matricula='" + matricula + '\'' +
                ", grado='" + grado + '\'' +
                ", grupo='" + grupo + '\'' +
                '}';
    }
}
