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

  @NotBlank
  private String nombre;

  @NotBlank
  @Email
  @Column(unique = true)
  private String email;

  @NotBlank
  @Column(unique = true)
  private String documento;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @Enumerated(EnumType.STRING)
  private EspecialidadEnum especialidad;

  @NotBlank
  private String telefono;


  @Embedded
  private Direccion direccion;


  // Constructor to initialize a doctor from a DTO containing personal and address data.
  public Medico(MedicoDTO datos) {
    this.nombre = datos.nombre();
    this.email = datos.email();
    this.documento = datos.documento();
    this.especialidad = datos.especialidad();
    this.telefono = datos.telefono();
    this.direccion = new Direccion(datos);
  }

}
