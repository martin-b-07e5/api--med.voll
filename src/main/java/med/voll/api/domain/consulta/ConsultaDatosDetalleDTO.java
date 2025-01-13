package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaDatosDetalleDTO(
    Long idConsulta,
    Long idMedico,
    Long idPaciente,
    @NotNull @Future @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime fechaInicio,
    @NotNull @Future @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime fechaFin
) {
  public ConsultaDatosDetalleDTO(Long idConsulta, Long idMedico, Long idPaciente, LocalDateTime fechaInicio) {
    this(idConsulta, idMedico, idPaciente, fechaInicio, fechaInicio.plusHours(1));
  }
}
