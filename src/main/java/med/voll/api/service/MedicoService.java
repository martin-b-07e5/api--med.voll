package med.voll.api.service;

import med.voll.api.entity.MedicoDTO;
import med.voll.api.entity.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/// Creates a service to encapsulate business logic and handle the conversion of the DTO to the Medico entity before persisting it.

@Service
public class MedicoService {

  private final MedicoValidatorMapper medicoValidatorMapper;
  private final MedicoRepository medicoRepository;
//  private final MedicoMapper medicoMapper;

  // Constructor-based dependency injection
  @Autowired
  public MedicoService(MedicoValidatorMapper medicoValidatorMapper, MedicoRepository medicoRepository) {
    this.medicoValidatorMapper = medicoValidatorMapper;
    this.medicoRepository = medicoRepository;
  }

  public void registrarMedico(MedicoDTO medicoDTO) {

    // Verificar unique email
    if (medicoRepository.existsByEmail(medicoDTO.email())) {
      throw new RuntimeException("E-mail IS ALREADY IN USE");
    }

    // Verificar unique documento
    if (medicoRepository.existsByDocumento(medicoDTO.documento())) {
      throw new RuntimeException("Document IS ALREADY IN USE");
    }

    // Validar el DTO antes de pasarlo al mapper para la conversión
    Medico medico = medicoValidatorMapper.toEntity(medicoDTO);

    // Si el DTO es válido, se guarda en la base de datos
    medicoRepository.save(medico);
  }

}
