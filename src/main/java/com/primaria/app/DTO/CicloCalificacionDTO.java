package com.primaria.app.DTO;

import java.util.List;

public class CicloCalificacionDTO {
    private String cicloEscolar;
    private List<GradoCalificacionDTO> grados;

    public CicloCalificacionDTO(String cicloEscolar, List<GradoCalificacionDTO> grados) {
        this.cicloEscolar = cicloEscolar;
        this.grados = grados;
    }

    // Getters y setters
    public String getCicloEscolar() { return cicloEscolar; }
    public void setCicloEscolar(String cicloEscolar) { this.cicloEscolar = cicloEscolar; }

    public List<GradoCalificacionDTO> getGrados() { return grados; }
    public void setGrados(List<GradoCalificacionDTO> grados) { this.grados = grados; }
}
