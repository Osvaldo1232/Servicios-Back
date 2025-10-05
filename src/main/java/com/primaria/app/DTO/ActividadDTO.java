package com.primaria.app.DTO;

import java.time.LocalDate;

public class ActividadDTO {

    private String id;
    private String idDocente;
  
    private String idCiclo;
    private String idMateria;
    private String idGrado;
    private String idGrupo;
    private String idTrimestre;
    private String idTipoEvaluacion;

    private String nombre;
    private LocalDate fecha;
    private Double valor;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdDocente() { return idDocente; }
    public void setIdDocente(String idDocente) { this.idDocente = idDocente; }

 
    public String getIdCiclo() { return idCiclo; }
    public void setIdCiclo(String idCiclo) { this.idCiclo = idCiclo; }

    public String getIdMateria() { return idMateria; }
    public void setIdMateria(String idMateria) { this.idMateria = idMateria; }

    public String getIdGrado() { return idGrado; }
    public void setIdGrado(String idGrado) { this.idGrado = idGrado; }

    public String getIdGrupo() { return idGrupo; }
    public void setIdGrupo(String idGrupo) { this.idGrupo = idGrupo; }

    public String getIdTrimestre() { return idTrimestre; }
    public void setIdTrimestre(String idTrimestre) { this.idTrimestre = idTrimestre; }

    public String getIdTipoEvaluacion() { return idTipoEvaluacion; }
    public void setIdTipoEvaluacion(String idTipoEvaluacion) { this.idTipoEvaluacion = idTipoEvaluacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
}
