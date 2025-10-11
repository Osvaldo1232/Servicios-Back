package com.primaria.app.DTO;

public class DirectorDTO extends UsuarioDTO {

    private String departamento;

    private String telefono;
    // Getters y Setters

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}