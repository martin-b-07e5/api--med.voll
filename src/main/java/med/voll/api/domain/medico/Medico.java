package med.voll.api.domain.medico;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.Direccion;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Medico")  /* default class name */
@Table(name = "medicos")
@Getter
@Setter
@AllArgsConstructor
public class Medico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_medico") // name of the column in the DB
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

  @NotNull
  @Enumerated(EnumType.STRING)
  private EspecialidadEnum especialidad;

  @NotBlank
  private String telefono;

  @Embedded
  private Direccion direccion;

  @Column
  private Boolean inactivo;

  // if you need to navigate from one doctor to all of his practices, then you should add a @OneToMany relation.
  // LAZY por defecto (@OneToMany, @ManyToMany):
//  @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  private List<Consulta> consultas;


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
    this.inactivo = datos.inactivo(); // this.inactivo = false
  }


  @Override
  public String toString() {
    return "Medico{" +
        "nombre='" + nombre + '\'' +
        ", email='" + email + '\'' +
        ", especialidad=" + especialidad +
        ", inactivo=" + inactivo +
        '}';
  }

}
