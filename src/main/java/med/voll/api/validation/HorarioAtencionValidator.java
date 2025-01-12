package med.voll.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class HorarioAtencionValidator implements ConstraintValidator<HorarioAtencion, LocalDateTime> {

  @Override
  public boolean isValid(LocalDateTime fecha, ConstraintValidatorContext context) {
    if (fecha == null) {
      return false;
    }
    int hora = fecha.getHour();
    return hora >= 7 && hora < 19;
  }
}
