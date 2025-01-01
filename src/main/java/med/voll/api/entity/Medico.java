package med.voll.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/* Consideraciones
Médico
  Nombre  » Solo letras. No vacío.
  Email  » Formato de email. No vacío.
  Documento  » Número de documento único. Solo números. No vacío.
  Especialidad  » ORTOPEDIA, PEDIATRIA, CARDIOLOGIA, GINECOLOGIA, NEUROLOGIA. No vacío.
  Teléfono  » Solo números. No vacío. */

@Entity(name = "Medico")  /*por default el nombre de la clase*/
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_medico") // nombre de la columna en la DB
  private Long idMedico;

  @NotBlank(message = "Field must not be null or empty.")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "The name can only contain letters and spaces.")
  private String nombre;

  @NotBlank(message = "Field must not be null or empty.")
  @Email(message = "The email must have a valid format.")
  private String email;

  @NotBlank(message = "Field must not be null or empty.")
  @Pattern(regexp = "^\\d+$", message = "The document must contain only numbers.")
  @Column(unique = true, nullable = false) // To ensure uniqueness in the database
  private String documento;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @Enumerated(EnumType.STRING)
  private EspecialidadEnum especialidad;


  @Embedded
  private Direccion direccion;


  // Constructor to initialize a doctor from a DTO containing personal and address data.
  public Medico(MedicoDTO datos) {
    this.nombre = datos.nombre();
    this.email = datos.email();
    this.documento = datos.documento();
    this.especialidad = datos.especialidad();
    this.direccion = new Direccion(datos);
  }

}
