package com.primaria.app.DTO;

public class PromedioCampoDTO {
    private String campoFormativo;
    private Double promedioCampo;

    public PromedioCampoDTO(String campoFormativo, Double promedioCampo) {
        this.campoFormativo = campoFormativo;
        this.promedioCampo = promedioCampo;
    }

    public String getCampoFormativo() {
        return campoFormativo;
    }

    public Double getPromedioCampo() {
        return promedioCampo;
    }
}
