package med.voll.api.service;

import med.voll.api.entity.Paciente;
import med.voll.api.entity.PacienteDTO;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.validation.PacienteValidatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/// Creates a service to encapsulate business logic and handle the conversion of the DTO to the Paciente entity before persisting it.

@Service
public class PacienteService {

  private final PacienteValidatorMapper pacienteValidatorMapper;
  private final PacienteRepository pacienteRepository;

  // Constructor-based dependency injection
  @Autowired
  public PacienteService(PacienteValidatorMapper pacienteValidatorMapper, PacienteRepository pacienteRepository) {
    this.pacienteValidatorMapper = pacienteValidatorMapper;
    this.pacienteRepository = pacienteRepository;
  }

  public void registrarPaciente(PacienteDTO pacienteDTO) {

    // Verificar unique email
    if (pacienteRepository.existsByEmail(pacienteDTO.email())) {
      throw new RuntimeException("E-mail IS ALREADY IN USE");
    }

    // Verificar unique documento
    if (pacienteRepository.existsByDocumento(pacienteDTO.documento())) {
      throw new RuntimeException("Document IS ALREADY IN USE");
    }

    // Validar el DTO antes de pasarlo al mapper para la conversión
    Paciente paciente = pacienteValidatorMapper.toEntity(pacienteDTO);

    // Si el DTO es válido, se guarda en la base de datos
//    pacienteRepository.save(paciente);
    pacienteRepository.save(paciente);
  }

}
