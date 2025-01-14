package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ConsultaListadoDTO(
    Long idConsulta,
    Long idMedico,
    String nombreMedico,
    Long idPaciente,
    String nombrePaciente,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime fechaInicio,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime fechaFin
) {
  // Constructor to map Consulta entity to ConsultaListadoDTO
  public ConsultaListadoDTO(Consulta consulta) {
    this(consulta.getIdConsulta(),
        consulta.getMedico().getIdMedico(),
        consulta.getMedico().getNombre(),  // Obtener el nombre del médico desde la entidad
        consulta.getPaciente().getIdPaciente(),
        consulta.getPaciente().getNombre(),
        consulta.getFecha(),
        consulta.getFecha().plusHours(1)
    );
  }

}
