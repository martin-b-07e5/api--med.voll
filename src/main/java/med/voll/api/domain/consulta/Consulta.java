package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "consultas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_Consulta") // name of the column in the DB
  Long idConsulta;

  //  EAGER por defecto (@ManyToOne, @OneToOne):
//  @ManyToOne(fetch = FetchType.EAGER)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_medico")
  private Medico medico;

  //  @ManyToOne(fetch = FetchType.EAGER)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_paciente")
  private Paciente paciente;

  @NotNull
  @Future
  LocalDateTime fecha;


}
