package med.voll.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_paciente") // nombre de la columna en la DB
  private Long idPaciente;

  @NotBlank
  private String nombre;

  @NotBlank
  @Email
  @Column(unique = true)
  private String email;

  @NotBlank
  @Column(unique = true)
  private String documento;

  @NotBlank
  private String telefono;


  @Embedded
  private Direccion direccion;


  // Constructor to initialize a doctor from a DTO containing personal and address data.
  public Paciente(PacienteDTO datos) {
    this.nombre = datos.nombre();
    this.email = datos.email();
    this.documento = datos.documento();
    this.telefono = datos.telefono();
    this.direccion = new Direccion(datos);
  }

}
