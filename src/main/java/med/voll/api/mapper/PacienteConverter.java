package med.voll.api.mapper;

import med.voll.api.entity.Paciente;
import med.voll.api.entity.PacienteDTO;
import org.springframework.stereotype.Component;

@Component
public class PacienteConverter {

  public Paciente toEntity(PacienteDTO dto) {
    return new Paciente(dto);
  }
}
