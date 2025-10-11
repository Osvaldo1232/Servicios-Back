package com.primaria.app.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "actividad")
public class Actividad {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_docente", nullable = false)
    private Profesor docente;

    // Ahora la actividad apunta a la asignación de materia a grado
    @ManyToOne
    @JoinColumn(name = "id_asignacion_materia_grado", nullable = false)
    private AsignacionMateriaGrado asignacionMateriaGrado;

    @ManyToOne
    @JoinColumn(name = "id_trimestre", nullable = false)
    private Trimestres trimestre;

    @ManyToOne
    @JoinColumn(name = "id_tipo_evaluacion", nullable = false)
    private Tipos_Evaluacion tipoEvaluacion;

    private String nombre;

    private LocalDate fecha;

    private Double valor;

    // Opcional: relación con los alumnos inscritos
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ActividadAlumno> actividadAlumnos;

    // GETTERS Y SETTERS

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Profesor getDocente() {
        return docente;
    }
    public void setDocente(Profesor docente) {
        this.docente = docente;
    }

    public AsignacionMateriaGrado getAsignacionMateriaGrado() {
        return asignacionMateriaGrado;
    }
    public void setAsignacionMateriaGrado(AsignacionMateriaGrado asignacionMateriaGrado) {
        this.asignacionMateriaGrado = asignacionMateriaGrado;
    }

    public Trimestres getTrimestre() {
        return trimestre;
    }
    public void setTrimestre(Trimestres trimestre) {
        this.trimestre = trimestre;
    }

    public Tipos_Evaluacion getTipoEvaluacion() {
        return tipoEvaluacion;
    }
    public void setTipoEvaluacion(Tipos_Evaluacion tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Set<ActividadAlumno> getActividadAlumnos() {
        return actividadAlumnos;
    }
    public void setActividadAlumnos(Set<ActividadAlumno> actividadAlumnos) {
        this.actividadAlumnos = actividadAlumnos;
    }
}
