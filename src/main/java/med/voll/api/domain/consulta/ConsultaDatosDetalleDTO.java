package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaDatosDetalleDTO(
    Long idConsulta,
    Long idMedico,
    Long idPaciente,
//    LocalDateTime fecha
    @NotNull @Future @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime fecha
) {
}
