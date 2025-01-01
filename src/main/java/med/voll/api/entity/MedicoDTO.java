package med.voll.api.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MedicoDTO(
    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    String nombre,
    String email,
    String documento,
    EspecialidadEnum especialidad,
    DireccionDTO direccion
) {

}
