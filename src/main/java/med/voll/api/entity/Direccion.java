package med.voll.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.Record.DireccionDTO;

/* Consideraciones
Dirección
  Calle  » Letras y números. No vacío.
  Numero  » Solo números. No vacío.
  piso  » Letras y números. No vacío.
  Ciudad  » Letras. No vacío.
  provincia  » Letras. No vacío.
  pais  » Letras. No vacío.
*/

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @NotBlank(message = "La calle no puede estar vacía")
  private String calle;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @NotBlank(message = "El número no puede estar vacío")
  @Min(value = 1, message = "El número debe ser mayor a 0")
  private String numero;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @NotBlank(message = "El piso no puede estar vacío")
  private String piso;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @NotBlank(message = "La ciudad no puede estar vacía")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "La ciudad solo puede contener letras y espacios")
  private String ciudad;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @NotBlank(message = "La provincia no puede estar vacía")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "La provincia solo puede contener letras y espacios")
  private String provincia;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @NotBlank(message = "El país no puede estar vacío")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El país solo puede contener letras y espacios")
  private String pais;


  // Constructor para inicializar los campos con parámetros
  public Direccion(DireccionDTO direccion) {
    this.calle = direccion.calle();
    this.numero = direccion.numero();
    this.piso = direccion.piso();
    this.ciudad = direccion.ciudad();
    this.provincia = direccion.provincia();
    this.pais = direccion.pais();
  }


}
