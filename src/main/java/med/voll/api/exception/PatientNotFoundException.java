package med.voll.api.exception;

public class PatientNotFoundException extends RuntimeException {

  public PatientNotFoundException(String message) {
    super(message);
  }

  public PatientNotFoundException(Long id) {
    super("Patient not found with ID: " + id);
  }

}
