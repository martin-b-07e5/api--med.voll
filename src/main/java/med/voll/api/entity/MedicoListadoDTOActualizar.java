package med.voll.api.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record MedicoListadoDTOActualizar(
    @NotNull Long id,
    String nombre,
    String documento,
    @Valid Direccion direccion
) {
}
