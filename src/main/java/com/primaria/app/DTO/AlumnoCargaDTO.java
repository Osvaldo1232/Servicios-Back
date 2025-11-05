package com.primaria.app.DTO;

public class AlumnoCargaDTO {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String matricula;
    private String curp;
    private String grado;
    private String grupo;
    private String tutorNombre;
    private String tutorApellidoPaterno;
    private String tutorApellidoMaterno;
    private String numeroTelefono;

    // Constructor
    public AlumnoCargaDTO(String nombre, String apellidoPaterno, String apellidoMaterno,
                          String matricula, String curp,
                          String grado, String grupo,
                          String tutorNombre, String tutorApellidoPaterno, String tutorApellidoMaterno, String numeroTelefono ) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.matricula = matricula;
        this.curp = curp;
        this.grado = grado;
        this.grupo = grupo;
        this.tutorNombre = tutorNombre;
        this.tutorApellidoPaterno = tutorApellidoPaterno;
        this.tutorApellidoMaterno = tutorApellidoMaterno;
        this.numeroTelefono=numeroTelefono;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getTutorNombre() { return tutorNombre; }
    public void setTutorNombre(String tutorNombre) { this.tutorNombre = tutorNombre; }

    public String getTutorApellidoPaterno() { return tutorApellidoPaterno; }
    public void setTutorApellidoPaterno(String tutorApellidoPaterno) { this.tutorApellidoPaterno = tutorApellidoPaterno; }

    public String getTutorApellidoMaterno() { return tutorApellidoMaterno; }
    public void setTutorApellidoMaterno(String tutorApellidoMaterno) { this.tutorApellidoMaterno = tutorApellidoMaterno; }
    
    

    public String getNumeroTutor() { return numeroTelefono; }
    public void setNumeroTutor(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }
}
