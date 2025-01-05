package med.voll.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import med.voll.api.controller.MedicoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Medico")  /*default class name*/
@Table(name = "medicos")
@Getter
@Setter
@AllArgsConstructor
public class Medico {

  private static final Logger logger = LoggerFactory.getLogger(Medico.class);

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


  public void actualizarDatos1(@Valid MedicoListadoDTOActualizar medicoListadoDTOActualizar) {

    if (medicoListadoDTOActualizar.nombre() != null) {
      this.nombre = medicoListadoDTOActualizar.nombre();
    }
    if (medicoListadoDTOActualizar.documento() != null) {
      this.documento = medicoListadoDTOActualizar.documento();
    }
    if (this.direccion != null) {
      this.direccion = this.direccion.actualizarDatos(medicoListadoDTOActualizar.direccion());
    } else {
      logger.info("direccion: {}", direccion);
      logger.info("getDireccion(): {}", getDireccion());
    }

  }


  public void actualizarDatos(@Valid MedicoListadoDTOActualizar medicoListadoDTOActualizar) {

    if (medicoListadoDTOActualizar.nombre() != null) {
      this.nombre = medicoListadoDTOActualizar.nombre();
    }
    if (medicoListadoDTOActualizar.documento() != null) {
      this.documento = medicoListadoDTOActualizar.documento();
    }

    // Verificar si la dirección no es null antes de intentar actualizarla
    if (medicoListadoDTOActualizar.direccion() != null) {
      logger.info("por que no entra acá");
      if (this.direccion != null) {
        this.direccion = this.direccion.actualizarDatos(medicoListadoDTOActualizar.direccion());
      } else {
        this.direccion = medicoListadoDTOActualizar.direccion();
      }
    } else {
      logger.info("¿por qué medicoListadoDTOActualizar.direccion() = null");
      logger.info("direccion: {}", direccion);
      logger.info("getDireccion(): {}", getDireccion());
      logger.info("calle: {}", getDireccion().getCalle());
    }
  }


}
