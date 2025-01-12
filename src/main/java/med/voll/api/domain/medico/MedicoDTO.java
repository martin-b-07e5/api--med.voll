package med.voll.api.domain.medico;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DireccionDTO;

public record MedicoDTO(

    @NotBlank
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The name can only contain letters and spaces")
    @JsonProperty("nombre")
    String nombre,

    @NotBlank(message = "The email is required.")
    @Email(message = "The email must have a valid format.")
    @JsonProperty("email")
    String email,

    @NotBlank(message = "must not be blank. Be careful")
    @Pattern(regexp = "^\\d+$", message = "The document must contain only numbers.")
    @JsonProperty("documento")
    String documento,

    @NotNull(message = "Field may not be null. However, the field can be empty.")
    @Enumerated(EnumType.STRING)
    @JsonProperty("especialidad")
    EspecialidadEnum especialidad,

    @NotBlank
    @Pattern(regexp = "^\\d{4,10}$", message = "The value must be a 4 to 10 digit number.")
    @JsonProperty("telefono")
    String telefono,

    @NotNull
    @Valid
    @JsonProperty("direccion")
    DireccionDTO direccion,

    @NotNull
    Boolean inactivo
) {

  // Constructor to map Medico entity to MedicoDTO
  public MedicoDTO(Medico medico) {
    this(
        medico.getNombre(),
        medico.getEmail(),
        medico.getDocumento(),
        medico.getEspecialidad(),
        medico.getTelefono(),
        new DireccionDTO(medico.getDireccion()),  // Assuming DireccionDTO has a constructor for Direccion
        medico.getInactivo()
    );
  }

}
