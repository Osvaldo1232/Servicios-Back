package com.primaria.app.DTO;



import com.primaria.app.Model.Estatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Objeto para actualizar solo el estatus de un profesor")
public class EstatusDTO {

    @NotNull(message = "El estatus no puede ser nulo")
    @Schema(description = "Nuevo estatus del profesor", example = "ACTIVO", allowableValues = {"ACTIVO", "INACTIVO", "SUSPENDIDO"})
    private Estatus estatus;

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
}
