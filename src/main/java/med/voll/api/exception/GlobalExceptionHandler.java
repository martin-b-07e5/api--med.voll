package med.voll.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private HttpServletRequest request;

  @ExceptionHandler(MedicoNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleMedicoNotFoundException(MedicoNotFoundException ex) {
    Map<String, Object> response = Map.of(
        "1-timestamp", LocalDateTime.now(),
        "2-status", HttpStatus.NOT_FOUND.value(),
        "3-error", "Not Found",
        "4-message", ex.getMessage(),
        "5-path", request.getRequestURI()
    );

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // Retorna 404 Not Found
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalStateException(IllegalStateException ex) {
    Map<String, Object> response = Map.of(
        "a-timestamp", LocalDateTime.now(),
        "b-status", HttpStatus.CONFLICT.value(),
        "c-error", "Conflict",
        "d-message", ex.getMessage(),
        "e-path", request.getRequestURI()
    );

    return new ResponseEntity<>(response, HttpStatus.CONFLICT); // Retorna 409 Conflict
  }
}
