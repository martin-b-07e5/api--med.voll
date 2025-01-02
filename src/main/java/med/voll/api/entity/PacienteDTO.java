package med.voll.api.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PacienteDTO(

    @NotBlank
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The name can only contain letters and spaces")
    String nombre,

    @NotBlank
    @Email(message = "The email must have a valid format.")
    String email,

    @NotBlank
    @Pattern(regexp = "^\\d+$", message = "The document must contain only numbers.")
    String documento,

    @NotBlank
    @Pattern(regexp = "^\\d{4,10}$", message = "The value must be a 4 to 10 digit number.")
    String telefono,

    @NotNull
    @Valid
    DireccionDTO direccion
) {

}
