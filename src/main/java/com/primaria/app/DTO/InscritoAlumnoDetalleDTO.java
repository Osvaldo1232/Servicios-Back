package com.primaria.app.DTO;



public class InscritoAlumnoDetalleDTO {

    private String idInscripcion;
    private String idAlumno;
    private String matricula;
    private String curp;
    private String nombreAlumno;
    private String nombreGrado;
    private String nombreGrupo;
    private String nombreProfesor;

    public InscritoAlumnoDetalleDTO() {}

    public InscritoAlumnoDetalleDTO(String idInscripcion, String idAlumno, String matricula, String curp,
                                    String nombreAlumno, String nombreGrado, String nombreGrupo, String nombreProfesor) {
        this.idInscripcion = idInscripcion;
        this.idAlumno = idAlumno;
        this.matricula = matricula;
        this.curp = curp;
        this.nombreAlumno = nombreAlumno;
        this.nombreGrado = nombreGrado;
        this.nombreGrupo = nombreGrupo;
        this.nombreProfesor = nombreProfesor;
    }

    // Getters y setters
    public String getIdInscripcion() { return idInscripcion; }
    public void setIdInscripcion(String idInscripcion) { this.idInscripcion = idInscripcion; }

    public String getIdAlumno() { return idAlumno; }
    public void setIdAlumno(String idAlumno) { this.idAlumno = idAlumno; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }

    public String getNombreGrado() { return nombreGrado; }
    public void setNombreGrado(String nombreGrado) { this.nombreGrado = nombreGrado; }

    public String getNombreGrupo() { return nombreGrupo; }
    public void setNombreGrupo(String nombreGrupo) { this.nombreGrupo = nombreGrupo; }

    public String getNombreProfesor() { return nombreProfesor; }
    public void setNombreProfesor(String nombreProfesor) { this.nombreProfesor = nombreProfesor; }
}
