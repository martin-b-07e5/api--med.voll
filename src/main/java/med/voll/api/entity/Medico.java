package med.voll.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.Record.EspecialidadEnum;
import med.voll.api.Record.DatosRegistroMedicoDTO;

/* Consideraciones
Médico
  Nombre  » Solo letras. No vacío.
  Especialidad  » ORTOPEDIA, PEDIATRIA, CARDIOLOGIA, GINECOLOGIA, NEUROLOGIA. No vacío.
  Documento  » Número de documento único. Solo números. No vacío.
  Email  » Formato de email. No vacío.
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

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @NotBlank(message = "El nombre no puede estar vacío")
  @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "El nombre solo puede contener letras y espacios")
  private String nombre;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @NotBlank(message = "El email no puede estar vacío")
  @Email(message = "El email debe tener un formato válido")
  private String email;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @NotBlank(message = "El documento no puede estar vacío")
  @Pattern(regexp = "^\\d+$", message = "El documento debe contener solo números")
  @Column(unique = true, nullable = false) // Para asegurar unicidad en la base de datos
  private String documento;

  @NotNull(message = "Field may not be null. However, the field can be empty.")
  @Enumerated(EnumType.STRING)
  private EspecialidadEnum especialidad;

  @Embedded
  private Direccion direccion;

  // Constructor para inicializar los campos con parámetros
  public Medico(DatosRegistroMedicoDTO datos) {
    this.nombre = datos.nombre();
    this.email = datos.email();
    this.documento = datos.documento();
    this.especialidad = datos.especialidad();
    this.direccion = new Direccion(
        datos.direccion().calle(),
        datos.direccion().numero(),
        datos.direccion().piso(),
        datos.direccion().ciudad(),
        datos.direccion().provincia(),
        datos.direccion().pais()
    );
  }


}
