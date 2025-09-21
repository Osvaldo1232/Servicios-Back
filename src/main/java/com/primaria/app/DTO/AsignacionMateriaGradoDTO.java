package com.primaria.app.DTO;

import java.util.UUID;

import com.primaria.app.Model.Estatus;
public class AsignacionMateriaGradoDTO {

	   private UUID id;
	    private UUID materiaId;
	    private UUID gradoId;
	    private Estatus estatus;
	    // Constructores

	    public AsignacionMateriaGradoDTO() {}

	    public AsignacionMateriaGradoDTO(UUID id, UUID materiaId, UUID gradoId) {
	        this.id = id;
	        this.materiaId = materiaId;
	        this.gradoId = gradoId;
	    }

	    // Getters y Setters

	    public UUID getId() {
	        return id;
	    }

	    public void setId(UUID id) {
	        this.id = id;
	    }

	    public UUID getMateriaId() {
	        return materiaId;
	    }

	    public void setMateriaId(UUID materiaId) {
	        this.materiaId = materiaId;
	    }

	    public UUID getGradoId() {
	        return gradoId;
	    }

	    public void setGradoId(UUID gradoId) {
	        this.gradoId = gradoId;
	    }

	    public Estatus getEstatus() {
	        return estatus;
	    }

	    public void setEstatus(Estatus estatus) {
	        this.estatus = estatus;
	    }
}
