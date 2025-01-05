package med.voll.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Medico")  /*default class name*/
@Table(name = "medicos")
@Getter
@Setter
@AllArgsConstructor
public class Medico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_medico") // nombre de la columna en la DB
  private Long id;

  @NotBlank
  @JsonProperty("nombre")
  private String nombre;

  @NotBlank
  @Email
  @Column(unique = true)
  @JsonProperty("email")
  private String email;

  @NotBlank
  @Column(unique = true)
  @JsonProperty("documento")
  private String documento;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @Enumerated(EnumType.STRING)
  @JsonProperty("especialidad")
  private EspecialidadEnum especialidad;

  @NotBlank
  @JsonProperty("telefono")
  private String telefono;

  @Embedded
  @JsonProperty("direccion")
  private Direccion direccion;

  @Column(nullable = false)
  private boolean activo = true;


  // default constructor
  public Medico() {
  }

  // Constructor to initialize a doctor from a DTO containing personal and address data.
  public Medico(MedicoDTO datos) {
    this.nombre = datos.nombre();
    this.email = datos.email();
    this.documento = datos.documento();
    this.especialidad = datos.especialidad();
    this.telefono = datos.telefono();
    this.direccion = new Direccion(datos);
  }


  @Override
  public String toString() {
    return "Medico{" +
        "idMedico=" + id +
        ", nombre='" + nombre + '\'' +
        ", email='" + email + '\'' +
        ", documento='" + documento + '\'' +
        ", especialidad=" + especialidad +
        ", telefono='" + telefono + '\'' +
        ", direccion=" + direccion +
        '}';
  }

}
