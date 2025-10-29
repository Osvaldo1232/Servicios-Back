package com.primaria.app.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;

@Entity
public class CicloEscolar {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private Estatus estatus;

    @CreationTimestamp
    @Column(name = "fecha_creado", updatable = false)
    private LocalDateTime fechaCreado;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Estatus getEstatus() { return estatus; }
    public void setEstatus(Estatus estatus) { this.estatus = estatus; }

    public LocalDateTime getFechaCreado() { return fechaCreado; }
    public void setFechaCreado(LocalDateTime fechaCreado) { this.fechaCreado = fechaCreado; }
}
