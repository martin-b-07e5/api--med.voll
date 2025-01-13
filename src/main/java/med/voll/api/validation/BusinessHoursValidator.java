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

    // Validar que sea de lunes a viernes
    if (fecha.getDayOfWeek().getValue() < 1 || fecha.getDayOfWeek().getValue() > 5) {
      return false; // Solo lunes a viernes
    }

    // Check if the time falls within the business hours (07:00 to 19:00)
    int businessHours = fecha.getHour();
    return businessHours >= 7 && businessHours < 19;
  }

}
