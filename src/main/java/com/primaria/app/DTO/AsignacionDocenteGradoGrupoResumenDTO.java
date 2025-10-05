package com.primaria.app.DTO;

public class AsignacionDocenteGradoGrupoResumenDTO {

    // Profesor
    private String idProfesor;
    private String nombreProfesor;
    private String rfc;
    private String clave;

    // Grado, Grupo, Ciclo
    private String nombreGrado;
    private String nombreGrupo;
    private String nombreCiclo;

    // Constructor
    public AsignacionDocenteGradoGrupoResumenDTO(String idProfesor, String nombreProfesor, String rfc, String clave,
                                                 String nombreGrado, String nombreGrupo, String nombreCiclo) {
        this.idProfesor = idProfesor;
        this.nombreProfesor = nombreProfesor;
        this.rfc = rfc;
        this.clave = clave;
        this.nombreGrado = nombreGrado;
        this.nombreGrupo = nombreGrupo;
        this.nombreCiclo = nombreCiclo;
    }

    // Getters y setters
    public String getIdProfesor() { return idProfesor; }
    public void setIdProfesor(String idProfesor) { this.idProfesor = idProfesor; }

    public String getNombreProfesor() { return nombreProfesor; }
    public void setNombreProfesor(String nombreProfesor) { this.nombreProfesor = nombreProfesor; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getNombreGrado() { return nombreGrado; }
    public void setNombreGrado(String nombreGrado) { this.nombreGrado = nombreGrado; }

    public String getNombreGrupo() { return nombreGrupo; }
    public void setNombreGrupo(String nombreGrupo) { this.nombreGrupo = nombreGrupo; }

    public String getNombreCiclo() { return nombreCiclo; }
    public void setNombreCiclo(String nombreCiclo) { this.nombreCiclo = nombreCiclo; }
}
