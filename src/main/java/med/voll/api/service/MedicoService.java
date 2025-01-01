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
  private final MedicoMapper medicoMapper;

  // Constructor-based dependency injection
  @Autowired
  public MedicoService(MedicoRepository medicoRepository, MedicoMapper medicoMapper) {
    this.medicoRepository = medicoRepository;
    this.medicoMapper = medicoMapper;
  }

  public void registrarMedico(MedicoDTO medicoDTO) {
    // Medico medico = new Medico(medicoDTO); // (before using MedicoMapper) Conversion from DTO to entity
    Medico medico = medicoMapper.toEntity(medicoDTO); // Conversion from DTO to entity using MedicoMapper
    /// Here, there could be more business logic, such as additional validations, etc.
    medicoRepository.save(medico); // Persistencia
  }
}
