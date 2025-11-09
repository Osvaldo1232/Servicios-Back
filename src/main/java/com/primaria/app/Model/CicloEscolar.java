package com.primaria.app.Model;

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

    @Column(name = "anio_inicio")
    private int anioInicio;

    @Column(name = "anio_fin")
    private int anioFin;
    
    @Enumerated(EnumType.STRING)
    private Estatus estatus;

    @CreationTimestamp
    @Column(name = "fecha_creado", updatable = false, columnDefinition = "datetime")
    private LocalDateTime fechaCreado;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getAnioInicio() { return anioInicio; }
    public void setAnioInicio(int anioInicio) { this.anioInicio = anioInicio; }

    public int getAnioFin() { return anioFin; }
    public void setAnioFin(int anioFin) { this.anioFin = anioFin; }

    public Estatus getEstatus() { return estatus; }
    public void setEstatus(Estatus estatus) { this.estatus = estatus; }

    public LocalDateTime getFechaCreado() { return fechaCreado; }
    public void setFechaCreado(LocalDateTime fechaCreado) { this.fechaCreado = fechaCreado; }
}
