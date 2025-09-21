package com.primaria.app.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;


@Entity
public class Actividad {

	   @Id
	    @GeneratedValue(generator = "UUID")
	    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	    @Column(name = "id", updatable = false, nullable = false, length = 36)
	    private String id;
    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Profesor docente;

    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Estudiante alumno;

    @ManyToOne
    @JoinColumn(name = "id_ciclo")
    private CicloEscolar ciclo;
    
    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_grado")
    private Grado grado;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_trimestre")
    private Trimestres trimestre;

    @ManyToOne
    @JoinColumn(name = "id_tipo_evaluacion")
    private Tipos_Evaluacion tipoEvaluacion;

    private String nombre;

    private LocalDate fecha;

    private Double valor;

    // Getters y setters

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

    public Estudiante getAlumno() {
        return alumno;
    }

    public void setAlumno(Estudiante alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
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
    
    public CicloEscolar getCiclo() {
        return ciclo;
    }

    public void setCiclo(CicloEscolar ciclo) {
        this.ciclo = ciclo;
    }
}
