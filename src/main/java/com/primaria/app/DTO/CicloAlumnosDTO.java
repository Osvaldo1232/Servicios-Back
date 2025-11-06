package com.primaria.app.DTO;

public class CicloAlumnosDTO {

	
	  private String id;
	    private String nombreCompleto;

	    public CicloAlumnosDTO(String id, String nombreCompleto) {
	        this.id = id;
	        this.nombreCompleto = nombreCompleto;
	    }

	    public String getId() { return id; }
	    public void setId(String id) { this.id = id; }

	    public String getNombreCompleto() { return nombreCompleto; }
	    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

}
