package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaDatosReservaDTO(
    Long idMedico, // no obligatorio
    @NotNull Long idPaciente,
    @NotNull @Future @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime fecha
) {
}
