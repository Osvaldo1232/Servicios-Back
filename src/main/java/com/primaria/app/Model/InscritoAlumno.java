package com.primaria.app.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscrito_alumno")
public class InscritoAlumno {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    // 🔹 Relación con el alumno (Estudiante)
    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Estudiante alumno;

    // 🔹 Nueva relación con la tabla asignacion_docente_grado_grupo
    // Esto reemplaza id_docente, id_grado, id_grupo, id_ciclo
    @ManyToOne
    @JoinColumn(name = "id_asignacion", nullable = false)
    private AsignacionDocenteGradoGrupo asignacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estatus estatus;

    @CreationTimestamp
    @Column(name = "fecha_inscripcion", updatable = false, columnDefinition = "datetime")
    private LocalDateTime fechaInscripcion;

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Estudiante getAlumno() {
        return alumno;
    }

    public void setAlumno(Estudiante alumno) {
        this.alumno = alumno;
    }

    public AsignacionDocenteGradoGrupo getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AsignacionDocenteGradoGrupo asignacion) {
        this.asignacion = asignacion;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
