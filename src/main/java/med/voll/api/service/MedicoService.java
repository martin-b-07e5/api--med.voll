package med.voll.api.service;

import med.voll.api.entity.MedicoDTO;
import med.voll.api.entity.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/// Creates a service to encapsulate business logic and handle the conversion of the DTO to the Medico entity before persisting it.

@Service
public class MedicoService {

  private final MedicoRepository medicoRepository;

  // Constructor-based dependency injection
  @Autowired
  public MedicoService(MedicoRepository medicoRepository) {
    this.medicoRepository = medicoRepository;
  }

  public void registrarMedico(MedicoDTO medicoDTO) {
    Medico medico = new Medico(medicoDTO); // Conversión del DTO a entidad
    medicoRepository.save(medico); // Persistencia
  }
}
