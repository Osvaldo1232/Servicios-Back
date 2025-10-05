package com.primaria.app.Model;



import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "asignacion_docente_grado_grupo")
public class AsignacionDocenteGradoGrupo {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Profesor docente;

    @ManyToOne
    @JoinColumn(name = "id_grado")
    private Grado grado;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_ciclo")
    private CicloEscolar ciclo;

    @Enumerated(EnumType.STRING)
    private Estatus estatus;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Profesor getDocente() { return docente; }
    public void setDocente(Profesor docente) { this.docente = docente; }

    public Grado getGrado() { return grado; }
    public void setGrado(Grado grado) { this.grado = grado; }

    public Grupo getGrupo() { return grupo; }
    public void setGrupo(Grupo grupo) { this.grupo = grupo; }

    public CicloEscolar getCiclo() { return ciclo; }
    public void setCiclo(CicloEscolar ciclo) { this.ciclo = ciclo; }

    public Estatus getEstatus() { return estatus; }
    public void setEstatus(Estatus estatus) { this.estatus = estatus; }
}
