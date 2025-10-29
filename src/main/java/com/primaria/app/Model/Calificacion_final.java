package com.primaria.app.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "calificacion_final")
public class Calificacion_final {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Estudiante alumno;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "id_trimestre", nullable = false)
    private Trimestres trimestre;

    @ManyToOne
    @JoinColumn(name = "id_ciclo", nullable = false)
    private CicloEscolar ciclo;

    @ManyToOne
    @JoinColumn(name = "id_grado", nullable = false)
    private Grado grado;

    @Column(name = "promedio", nullable = false)
    private Double promedio;

    // Constructores
    public Calificacion_final() {}

    public Calificacion_final(Estudiante alumno, Materia materia, Trimestres trimestre, CicloEscolar ciclo, Grado grado, Double promedio) {
        this.alumno = alumno;
        this.materia = materia;
        this.trimestre = trimestre;
        this.ciclo = ciclo;
        this.grado = grado;
        this.promedio = promedio;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Estudiante getAlumno() { return alumno; }
    public void setAlumno(Estudiante alumno) { this.alumno = alumno; }

    public Materia getMateria() { return materia; }
    public void setMateria(Materia materia) { this.materia = materia; }

    public Trimestres getTrimestre() { return trimestre; }
    public void setTrimestre(Trimestres trimestre) { this.trimestre = trimestre; }

    public CicloEscolar getCiclo() { return ciclo; }
    public void setCiclo(CicloEscolar ciclo) { this.ciclo = ciclo; }

    public Grado getGrado() { return grado; }
    public void setGrado(Grado grado) { this.grado = grado; }

    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
}
