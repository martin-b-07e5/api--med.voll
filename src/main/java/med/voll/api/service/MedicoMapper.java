package med.voll.api.service;

import med.voll.api.entity.Medico;
import med.voll.api.entity.MedicoDTO;
import org.springframework.stereotype.Component;

@Component  // ❗Add this annotation so that Spring registers the class as a bean.
public class MedicoMapper {


  /// This class is responsible for converting a MedicoDTO to a Medico entity.
  /// It encapsulates the logic for the conversion, making it reusable and testable.
  public Medico toEntity(MedicoDTO dto) {
    return new Medico(dto);
  }

}
