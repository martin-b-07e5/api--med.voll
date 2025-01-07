package med.voll.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicoNotFoundException extends RuntimeException {

  public MedicoNotFoundException(String message) {
    super(message);
  }

  public MedicoNotFoundException(Long id) {
    super("Médico no encontrado con ID: " + id);
  }


}
