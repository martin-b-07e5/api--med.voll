package med.voll.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class BusinessHoursValidator implements ConstraintValidator<BusinessHours, LocalDateTime> {

  @Override
  public boolean isValid(LocalDateTime fecha, ConstraintValidatorContext context) {
    if (fecha == null) {
      return false;
    }
    int hora = fecha.getHour();
    return hora >= 7 && hora < 19;
  }
}
