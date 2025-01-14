package med.voll.api.validation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidarAnticipacionConsulta {

  public void validarAnticipacionConsulta(@NotNull @Future LocalDateTime fecha) {
    LocalDateTime now = LocalDateTime.now();
    if (fecha.isBefore(now.plusMinutes(30))) {
      throw new IllegalArgumentException("Appointments must be scheduled at least 30 minutes in advance.");
    }
  }

}
