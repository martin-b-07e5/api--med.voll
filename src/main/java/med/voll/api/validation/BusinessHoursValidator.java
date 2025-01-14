package med.voll.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

//    // Validar que la hora esté entre 07:00 y 18:00 (hora de inicio de la consulta)
//    int hour = fecha.getHour();
//    int minute = fecha.getMinute();
//
//    // Citas deben comenzar como máximo a las 18:00:00
//    boolean withinBusinessHours = (hour > 7 || (hour == 7 && minute >= 0)) && (hour < 18 || (hour == 18 && minute == 0));
//
//    return withinBusinessHours;
    // Validar que la hora esté entre 07:00 y 18:00
    LocalTime horaInicio = LocalTime.of(7, 0);
    LocalTime horaFin = LocalTime.of(18, 0);
    LocalTime horaConsulta = fecha.toLocalTime();

    return !horaConsulta.isBefore(horaInicio) && !horaConsulta.isAfter(horaFin);
  }

}
