package com.primaria.app.DTO;

import java.util.List;

public class AlumnoInfoDTO {
    private String cicloId;
    private String grado;
    private String grupo;
    private List<MateriaCalificacionDTO> materias;

    public AlumnoInfoDTO(String cicloId, String grado, String grupo, List<MateriaCalificacionDTO> materias) {
        this.cicloId = cicloId;
        this.grado = grado;
        this.grupo = grupo;
        this.materias = materias;
    }

    // Getters y setters
    public String getCicloId() { return cicloId; }
    public void setCicloId(String cicloId) { this.cicloId = cicloId; }

    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public List<MateriaCalificacionDTO> getMaterias() { return materias; }
    public void setMaterias(List<MateriaCalificacionDTO> materias) { this.materias = materias; }
}
