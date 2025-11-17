package com.primaria.app.DTO;

import com.primaria.app.Model.Estatus;

public class InscritoAlumnoInfoBasicaDTO {

    private String alumnoId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String matricula;
    private String curp;

    private String gradoId;
    private String gradoNombre;

    private String grupoId;
    private String grupoNombre;

    private Estatus estatus;

    // 🔹 Nuevos campos solicitados
    private boolean ultimaInscripcion;  
    private boolean tutorCiclo;

    public InscritoAlumnoInfoBasicaDTO() {}

    public InscritoAlumnoInfoBasicaDTO(
            String alumnoId,
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String matricula,
            String curp,
            String gradoId,
            String gradoNombre,
            String grupoId,
            String grupoNombre,
            Estatus estatus,
            boolean ultimaInscripcion,
            boolean tutorCiclo
    ) {
        this.alumnoId = alumnoId;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.matricula = matricula;
        this.curp = curp;
        this.gradoId = gradoId;
        this.gradoNombre = gradoNombre;
        this.grupoId = grupoId;
        this.grupoNombre = grupoNombre;
        this.estatus = estatus;
        this.ultimaInscripcion = ultimaInscripcion;
        this.tutorCiclo = tutorCiclo;
    }

    // Getters y Setters

    public String getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getGradoId() {
        return gradoId;
    }

    public void setGradoId(String gradoId) {
        this.gradoId = gradoId;
    }

    public String getGradoNombre() {
        return gradoNombre;
    }

    public void setGradoNombre(String gradoNombre) {
        this.gradoNombre = gradoNombre;
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public boolean isUltimaInscripcion() {
        return ultimaInscripcion;
    }

    public void setUltimaInscripcion(boolean ultimaInscripcion) {
        this.ultimaInscripcion = ultimaInscripcion;
    }

    public boolean isTutorCiclo() {
        return tutorCiclo;
    }

    public void setTutorCiclo(boolean tutorCiclo) {
        this.tutorCiclo = tutorCiclo;
    }
}
