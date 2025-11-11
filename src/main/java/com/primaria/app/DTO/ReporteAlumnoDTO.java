package com.primaria.app.DTO;

import java.math.BigDecimal;
import java.util.List;

public class ReporteAlumnoDTO {

    private String idAlumno;
    private String nombreCompleto;
    private String grado;
    private String grupo;
    private List<PromedioCampoFormativoDTO> camposFormativos;
    private BigDecimal promedioGeneral;

    public ReporteAlumnoDTO(String idAlumno, String nombreCompleto, String grado, String grupo,
                            List<PromedioCampoFormativoDTO> camposFormativos, BigDecimal promedioGeneral) {
        this.idAlumno = idAlumno;
        this.nombreCompleto = nombreCompleto;
        this.grado = grado;
        this.grupo = grupo;
        this.camposFormativos = camposFormativos;
        this.promedioGeneral = promedioGeneral;
    }

    // Getters
    public String getIdAlumno() { return idAlumno; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getGrado() { return grado; }
    public String getGrupo() { return grupo; }
    public List<PromedioCampoFormativoDTO> getCamposFormativos() { return camposFormativos; }
    public BigDecimal getPromedioGeneral() { return promedioGeneral; }
}
