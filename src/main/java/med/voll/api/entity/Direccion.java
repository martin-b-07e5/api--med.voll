package med.voll.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class Direccion {

  @NotBlank(message = "La calle no puede estar vacía")
  @Column(name = "calle")
  @JsonProperty("calle")
  private String calle;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @Min(value = 1, message = "El número debe ser mayor a 0")
  @Column(name = "direccion_numero")
  @JsonProperty("numero")
  private Integer numero;

  @NotBlank(message = "El piso no puede estar vacío")
  @Column(name = "direccion_piso")
  @JsonProperty("piso")
  private String piso;

  @NotBlank(message = "La ciudad no puede estar vacía")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "La ciudad solo puede contener letras y espacios")
  @Column(name = "direccion_ciudad")
  @JsonProperty("ciudad")
  private String ciudad;

  @NotBlank(message = "La provincia no puede estar vacía")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "La provincia solo puede contener letras y espacios")
  @Column(name = "direccion_provincia")
  @JsonProperty("provincia")
  private String provincia;

  @NotBlank(message = "El país no puede estar vacío")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El país solo puede contener letras y espacios")
  @Column(name = "direccion_pais")
  @JsonProperty("pais")
  private String pais;

  /// @NoArgsConstructor and classes annotated with @Embeddable may not work.
  /// ❗In Spring and JPA, it is crucial that embeddable classes have an EXPLICITLY available no-argument constructor.
  /// ❗However, Lombok generates this constructor implicitly, and in some cases, JPA does not recognize it properly.
  public Direccion() {
  }

  // Constructor to initialize an address from the address data included in a DTO
  public Direccion(MedicoDTO datos) {
    this.calle = datos.direccion().calle();
    this.numero = datos.direccion().numero();
    this.piso = datos.direccion().piso();
    this.ciudad = datos.direccion().ciudad();
    this.provincia = datos.direccion().provincia();
    this.pais = datos.direccion().pais();
  }

  // Constructor para inicializar Direccion desde un PacienteDTO
  public Direccion(PacienteDTO datos) {
    this.calle = datos.direccion().calle();
    this.numero = datos.direccion().numero();
    this.piso = datos.direccion().piso();
    this.ciudad = datos.direccion().ciudad();
    this.provincia = datos.direccion().provincia();
    this.pais = datos.direccion().pais();
  }


  @Override
  public String toString() {
    return "Direccion{" +
        "calle='" + calle + '\'' +
        ", numero=" + numero +
        ", piso='" + piso + '\'' +
        ", ciudad='" + ciudad + '\'' +
        ", provincia='" + provincia + '\'' +
        ", pais='" + pais + '\'' +
        '}';
  }


  public Direccion actualizarDatos(Direccion direccion) {
    this.calle = direccion.calle;
    this.numero = direccion.numero;
    this.piso = direccion.piso;
    this.ciudad = direccion.ciudad;
    this.provincia = direccion.provincia;
    this.pais = direccion.pais;
//    return this;
    return direccion;
  }


}
