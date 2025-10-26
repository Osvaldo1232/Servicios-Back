package com.primaria.app.DTO;

public class AsignacionDocenteGradoGrupoDTO {

    private String id;
    private String idDocente;
    private String idGrado;
    private String idGrupo;
    private String idCiclo;
    private String estatus;
    private String fechaCreado; // ISO string, por ejemplo "2025-10-25T21:00:00"

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdDocente() { return idDocente; }
    public void setIdDocente(String idDocente) { this.idDocente = idDocente; }

    public String getIdGrado() { return idGrado; }
    public void setIdGrado(String idGrado) { this.idGrado = idGrado; }

    public String getIdGrupo() { return idGrupo; }
    public void setIdGrupo(String idGrupo) { this.idGrupo = idGrupo; }

    public String getIdCiclo() { return idCiclo; }
    public void setIdCiclo(String idCiclo) { this.idCiclo = idCiclo; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public String getFechaCreado() { return fechaCreado; }
    public void setFechaCreado(String fechaCreado) { this.fechaCreado = fechaCreado; }
}
