package med.voll.api.exception;

public class NoAvailableDoctorException extends RuntimeException {
  public NoAvailableDoctorException(String message) {
    super(message);
  }
}
