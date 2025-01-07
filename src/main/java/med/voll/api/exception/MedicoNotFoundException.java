package med.voll.api.exception;

public class MedicoNotFoundException extends RuntimeException {

  public MedicoNotFoundException(String message) {
    super(message);
  }

  public MedicoNotFoundException(Long id) {
    super("Médico no encontrado con ID: " + id);
  }

}
