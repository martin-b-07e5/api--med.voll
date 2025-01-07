package med.voll.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MedicoNotFoundException.class)
  public Map<String, Object> handleMedicoNotFoundException(MedicoNotFoundException ex) {
    return Map.of(
        "timestamp", LocalDateTime.now(),
        "status", HttpStatus.NOT_FOUND.value(),
        "error", "Not Found",
        "message", ex.getMessage(),
        "path", "/medicos/{id}" // Puedes ajustar dinámicamente el path si lo necesitas
    );
  }
}
