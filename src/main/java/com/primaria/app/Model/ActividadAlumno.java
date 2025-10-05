package com.primaria.app.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class ActividadAlumno {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Estudiante alumno;

    private Double valorObtenido; // valor obtenido por el alumno en esta actividad

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Actividad getActividad() { return actividad; }
    public void setActividad(Actividad actividad) { this.actividad = actividad; }

    public Estudiante getAlumno() { return alumno; }
    public void setAlumno(Estudiante alumno) { this.alumno = alumno; }

    public Double getValorObtenido() { return valorObtenido; }
    public void setValorObtenido(Double valorObtenido) { this.valorObtenido = valorObtenido; }
}
