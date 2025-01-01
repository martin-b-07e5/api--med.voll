package med.voll.api.service;

import med.voll.api.Record.DatosRegistroMedicoDTO;
import med.voll.api.entity.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

  private final MedicoRepository medicoRepository;

  // constuructor dependency injection
  @Autowired
  public MedicoService(MedicoRepository medicoRepository) {
    this.medicoRepository = medicoRepository;
  }

  // Methods
  public void registrarMedico(DatosRegistroMedicoDTO datosRegistroMedicoDTO) {
    Medico medico = new Medico(datosRegistroMedicoDTO); // Conversión del DTO a entidad
    medicoRepository.save(medico); // Persistencia
  }
}
