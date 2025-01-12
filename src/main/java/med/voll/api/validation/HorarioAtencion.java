package med.voll.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HorarioAtencionValidator.class)
public @interface HorarioAtencion {
  String message() default "The time must be between 07:00 and 19:00.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
