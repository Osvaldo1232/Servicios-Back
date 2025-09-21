package com.primaria.app.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Profesor extends Usuario {

    private String especialidad;
    
    private String telefono;
    private String rfc;
    private String clave_Pesupuestal;
    
    @Enumerated(EnumType.STRING)
    private Estatus estatus; 
    
    // Getters y Setters

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    
    
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
 
    public String getclave_Pesupuestal() {
        return clave_Pesupuestal;
    }

    public void setclave_Pesupuestal(String clave_Pesupuestal) {
        this.clave_Pesupuestal = clave_Pesupuestal;
    }
    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
}