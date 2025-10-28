package com.primaria.app.DTO;

import java.util.UUID;

import com.primaria.app.Model.Estatus;

public class TutorDTO {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String correo;
   
    private String telefono;
    private Estatus estatus;
    // Constructores

    public TutorDTO() {}

    public TutorDTO(UUID id, String nombre, String apellidos, String correo,  String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        
        this.telefono = telefono;
    }

    // Getters y setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

  

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
}