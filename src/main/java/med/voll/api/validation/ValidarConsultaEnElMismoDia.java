package med.voll.api.validation;

import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidarConsultaEnElMismoDia {

  @Autowired
  private ConsultaRepository consultaRepository;

  public void validarConsultaEnElMismoDia(Long idPaciente, LocalDateTime fecha) {
    boolean consultaEnElMismoDia = consultaRepository.existsConsultaEnElMismoDia(idPaciente, fecha);
    if (consultaEnElMismoDia) {
      throw new IllegalArgumentException("The patient already has a consultation scheduled on the same day.");
    }
  }

}
