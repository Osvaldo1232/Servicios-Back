package com.primaria.app.DTO;

public class DocenteMateriaDTO {
    private String grado;
    private String grupo;
    private String materia;
 

    public DocenteMateriaDTO(String grado, String grupo, String materia) {
        this.grado = grado;
        this.grupo = grupo;
        this.materia = materia;
        
    }

    public String getGrado() { return grado; }
    public String getGrupo() { return grupo; }
    public String getMateria() { return materia; }
   
}
