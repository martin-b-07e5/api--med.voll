package med.voll.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BusinessHoursValidator.class)
public @interface BusinessHours {
  String message() default "The appointment must be made from Monday to Friday, between 07:00 and 18:00 hours.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
