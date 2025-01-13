package med.voll.api.domain.paciente;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import med.voll.api.domain.direccion.Direccion;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "pacientes")
@Getter
@Setter
@AllArgsConstructor
public class Paciente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_paciente") // nombre de la columna en la DB
  private Long idPaciente;

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

  @NotBlank
  @JsonProperty("telefono")
  private String telefono;


  @Embedded
  @JsonProperty("direccion")
  private Direccion direccion;

  @Column
  private Boolean inactivo;


  // default constructor
  public Paciente() {
  }

  // Constructor to initialize a paciente from a DTO containing personal and address data.
  public Paciente(PacienteDTO datos) {
    this.nombre = datos.nombre();
    this.email = datos.email();
    this.documento = datos.documento();
    this.telefono = datos.telefono();
    this.direccion = new Direccion(datos);
    this.inactivo = datos.inactivo(); // this.inactivo = false
  }


  @Override
  public String toString() {
    return "Paciente{" +
        "idPaciente=" + idPaciente +
        ", nombre='" + nombre + '\'' +
        ", email='" + email + '\'' +
        ", documento='" + documento + '\'' +
        ", telefono='" + telefono + '\'' +
        ", direccion=" + direccion +
        ", inactivo=" + inactivo +
        '}';
  }


}
