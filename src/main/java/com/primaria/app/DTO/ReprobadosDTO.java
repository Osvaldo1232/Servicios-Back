package com.primaria.app.DTO;

import java.util.List;

public class ReprobadosDTO {

    private String idAlumno;
    private String nombreCompleto;
    private String grado;
    private String grupo;
    private String ciclo;
    private List<MateriaReprobadaDTO> materias;

    public ReprobadosDTO(String idAlumno, String nombreCompleto, String grado,
                         String grupo, String ciclo, List<MateriaReprobadaDTO> materias) {
        this.idAlumno = idAlumno;
        this.nombreCompleto = nombreCompleto;
        this.grado = grado;
        this.grupo = grupo;
        this.ciclo = ciclo;
        this.materias = materias;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
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

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public List<MateriaReprobadaDTO> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriaReprobadaDTO> materias) {
        this.materias = materias;
    }

}  