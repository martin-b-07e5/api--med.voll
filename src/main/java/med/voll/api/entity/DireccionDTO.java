package med.voll.api.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DireccionDTO(
    @NotBlank
    String calle,
    @NotNull
    Integer numero,
    @NotBlank
    String piso,
    @NotBlank
    String ciudad,
    @NotBlank
    String provincia,
    @NotBlank
    String pais
) {
}
