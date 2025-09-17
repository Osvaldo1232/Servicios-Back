package com.primaria.app.Model;


import jakarta.persistence.Entity;

@Entity
public class Estudiante extends Usuario {

    private String matricula;
    private String curp;

    // Getters y Setters

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
}