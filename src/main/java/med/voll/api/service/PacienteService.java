package med.voll.api.service;

import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteDTO;
import med.voll.api.domain.paciente.PacienteListadoDTO;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.mapper.PacienteValidatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/// Creates a service to encapsulate business logic and handle the conversion of the DTO to the Paciente entity before persisting it.

@Service
public class PacienteService {

  private final PacienteValidatorMapper pacienteValidatorMapper;
  private final med.voll.api.repository.PacienteRepository pacienteRepository;
  private final MedicoRepository medicoRepository;

  // Constructor-based dependency injection
  @Autowired
  public PacienteService(PacienteValidatorMapper pacienteValidatorMapper, med.voll.api.repository.PacienteRepository pacienteRepository,
                         MedicoRepository medicoRepository) {
    this.pacienteValidatorMapper = pacienteValidatorMapper;
    this.pacienteRepository = pacienteRepository;
    this.medicoRepository = medicoRepository;
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


  public List<Paciente> listarPacientes() {
    return pacienteRepository.findAll();
  }

  public List<PacienteListadoDTO> listarDTO() {
    List<Paciente> pacientes = pacienteRepository.findAll();
    // Mapea la lista de Medico a MedicoListadoDTO

//    List<PacienteListadoDTO> pacienteListadoDTO = paciente.stream()
//        .map(paciente -> new PacienteListadoDTO(
//            paciente.getNombre(),
//            paciente.getDocumento(),
//            paciente.getEmail()
//        ))
//        .collect(Collectors.toList());
//    return medicoListadoDTOS;

    return pacientes.stream()
        .map(PacienteListadoDTO::new)
        .toList();
  }

  public Page<PacienteListadoDTO> listarPaginado(Pageable pageable) {
    Page<Paciente> pacientes = pacienteRepository.findAll(pageable);
    return pacientes.map(PacienteListadoDTO::new);
  }


}
