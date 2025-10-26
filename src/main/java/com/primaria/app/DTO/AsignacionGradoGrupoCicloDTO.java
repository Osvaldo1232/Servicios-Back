package com.primaria.app.DTO;

public class AsignacionGradoGrupoCicloDTO {

    // Grado
    private String idGrado;
    private String nombreGrado;

    // Grupo
    private String idGrupo;
    private String nombreGrupo;

    // Ciclo
    private String idCiclo;
    private String ciclo; // formato "aaaa/MM/dd-aaaa/MM/dd"

    // Constructor completo
    public AsignacionGradoGrupoCicloDTO(String idGrado, String nombreGrado,
                                        String idGrupo, String nombreGrupo,
                                        String idCiclo, String ciclo) {
        this.idGrado = idGrado;
        this.nombreGrado = nombreGrado;
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.idCiclo = idCiclo;
        this.ciclo = ciclo;
    }

    // Getters y setters
    public String getIdGrado() { return idGrado; }
    public void setIdGrado(String idGrado) { this.idGrado = idGrado; }

    public String getNombreGrado() { return nombreGrado; }
    public void setNombreGrado(String nombreGrado) { this.nombreGrado = nombreGrado; }

    public String getIdGrupo() { return idGrupo; }
    public void setIdGrupo(String idGrupo) { this.idGrupo = idGrupo; }

    public String getNombreGrupo() { return nombreGrupo; }
    public void setNombreGrupo(String nombreGrupo) { this.nombreGrupo = nombreGrupo; }

    public String getIdCiclo() { return idCiclo; }
    public void setIdCiclo(String idCiclo) { this.idCiclo = idCiclo; }

    public String getCiclo() { return ciclo; }
    public void setCiclo(String ciclo) { this.ciclo = ciclo; }
}
