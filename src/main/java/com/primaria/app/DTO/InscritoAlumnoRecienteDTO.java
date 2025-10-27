package com.primaria.app.DTO;

public class InscritoAlumnoRecienteDTO {
    private String id;
    private String idGrado;
    private String nombreGrado;
    private String idGrupo;
    private String nombreGrupo;
    private String idCiclo;
    private String ciclo; // formato yyyy-MM-dd/yyyy-MM-dd
    private String nombreProfesorCompleto;
    private String telefonoProfesor;

    public InscritoAlumnoRecienteDTO(
            String id,
            String idGrado,
            String nombreGrado,
            String idGrupo,
            String nombreGrupo,
            String idCiclo,
            String ciclo,
            String nombreProfesorCompleto,
            String telefonoProfesor
    ) {
        this.id = id;
        this.idGrado = idGrado;
        this.nombreGrado = nombreGrado;
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.idCiclo = idCiclo;
        this.ciclo = ciclo;
        this.nombreProfesorCompleto = nombreProfesorCompleto;
        this.telefonoProfesor = telefonoProfesor;
    }

    public String getId() { return id; }
    public String getIdGrado() { return idGrado; }
    public String getNombreGrado() { return nombreGrado; }
    public String getIdGrupo() { return idGrupo; }
    public String getNombreGrupo() { return nombreGrupo; }
    public String getIdCiclo() { return idCiclo; }
    public String getCiclo() { return ciclo; }
    public String getNombreProfesorCompleto() { return nombreProfesorCompleto; }
    public String getTelefonoProfesor() { return telefonoProfesor; }
}
