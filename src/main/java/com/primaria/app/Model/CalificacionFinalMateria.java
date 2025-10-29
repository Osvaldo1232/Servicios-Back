package com.primaria.app.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "calificacion_Final_Materia")
public class CalificacionFinalMateria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    // 🔗 Relaciones con Alumno, Materia y CicloEscolar
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alumno", nullable = false)
    private Estudiante alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ciclo_escolar", nullable = false)
    private CicloEscolar cicloEscolar;

    // 📊 Calificación promedio
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal promedio;

    // 🕒 Fecha de creación automática
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false, columnDefinition = "datetime")
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grado", nullable = false)
    private Grado grado;
    
    // 🧱 Constructores
    public CalificacionFinalMateria() {}

    public CalificacionFinalMateria(Estudiante alumno, Materia materia, CicloEscolar cicloEscolar, Grado grado, BigDecimal promedio) {
        this.alumno = alumno;
        this.materia = materia;
        this.cicloEscolar = cicloEscolar;
        this.grado = grado;
        this.promedio = promedio;
    }


    // 🧩 Getters y Setters
    public String getId() { return id; }

    public Estudiante getAlumno() { return alumno; }
    public void setAlumno(Estudiante alumno) { this.alumno = alumno; }

    public Materia getMateria() { return materia; }
    public void setMateria(Materia materia) { this.materia = materia; }

    public CicloEscolar getCicloEscolar() { return cicloEscolar; }
    public void setCicloEscolar(CicloEscolar cicloEscolar) { this.cicloEscolar = cicloEscolar; }

    public BigDecimal getPromedio() { return promedio; }
    public void setPromedio(BigDecimal promedio) { this.promedio = promedio; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    
    public Grado getGrado() { return grado; }
    public void setGrado(Grado grado) { this.grado = grado; }
}
