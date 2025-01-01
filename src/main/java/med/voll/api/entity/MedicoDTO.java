package med.voll.api.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MedicoDTO(

    @NotBlank
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    String nombre,

    @NotBlank
    @Email(message = "The email must have a valid format.")
    String email,

    @NotBlank
    @Pattern(regexp = "^\\d+$", message = "The document must contain only numbers.")
    String documento,

    @NotNull(message = "Field may not be null. However, the field can be empty.")
    @Enumerated(EnumType.STRING)
    EspecialidadEnum especialidad,


    @NotNull
    @Valid
    DireccionDTO direccion
) {

}
