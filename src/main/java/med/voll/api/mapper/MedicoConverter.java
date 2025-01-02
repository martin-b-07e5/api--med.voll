package med.voll.api.mapper;

import med.voll.api.entity.Medico;
import med.voll.api.entity.MedicoDTO;
import org.springframework.stereotype.Component;

@Component
public class MedicoConverter {

  public Medico toEntity(MedicoDTO dto) {
    return new Medico(dto);
  }
}
