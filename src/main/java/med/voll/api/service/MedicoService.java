package med.voll.api.service;

import med.voll.api.entity.MedicoListadoDTO;
import med.voll.api.mapper.MedicoValidatorMapper;
import med.voll.api.entity.MedicoDTO;
import med.voll.api.entity.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/// Creates a service to encapsulate business logic and handle the conversion of the DTO to the Medico entity before persisting it.


/*Consideraciones

Actualización de Médicos

Información permitida para actualización
  - Nombre
  - Documento
  - Dirección

Reglas del negocio
  - No permitir actualizar email, especialidad y documento.
*/

@Service
public class MedicoService {

//  @Autowired
//  private MedicoRepository medicoRepository;

//  @Autowired
//  MedicoValidatorMapper medicoValidatorMapper;

  private final MedicoRepository medicoRepository;
  private final MedicoValidatorMapper medicoValidatorMapper;

  // Constructor-based dependency injection
  public MedicoService(MedicoRepository medicoRepository, MedicoValidatorMapper medicoValidatorMapper) {
    this.medicoRepository = medicoRepository;
    this.medicoValidatorMapper = medicoValidatorMapper;
  }


  // Constructor-based dependency injection
//  public MedicoService(MedicoValidatorMapper medicoValidatorMapper, MedicoRepository medicoRepository) {
//    this.medicoValidatorMapper = medicoValidatorMapper;
//    this.medicoRepository = medicoRepository;
//  }

//  public MedicoService(MedicoRepository medicoRepository) {
//    this.medicoRepository = medicoRepository;
//  }

  // create
  public void registrarMedico(MedicoDTO medicoDTO) {

    // Verify unique email
    if (medicoRepository.existsByEmail(medicoDTO.email())) {
      throw new RuntimeException("E-mail IS ALREADY IN USE");
    }

    // Verify unique documento
    if (medicoRepository.existsByDocumento(medicoDTO.documento())) {
      throw new RuntimeException("Document IS ALREADY IN USE");
    }

    // Validar el DTO antes de pasarlo al mapper para la conversión
    Medico medico = medicoValidatorMapper.toEntity(medicoDTO);

    // Si el DTO es válido, se guarda en la base de datos
    medicoRepository.save(medico);
  }


  // read
  public List<Medico> listarMedicos() {
    return medicoRepository.findAll();
  }

  public List<MedicoListadoDTO> listarDTO() {
    List<Medico> medicos = medicoRepository.findAll();
    // Mapea la lista de Medico a MedicoListadoDTO

//    List<MedicoListadoDTO> medicoListadoDTOS = medicos.stream()
//        .map(medico -> new MedicoListadoDTO(
//            medico.getNombre(),
//            medico.getEspecialidad().toString(),
//            medico.getDocumento(),
//            medico.getEmail()
//        ))
//        .collect(Collectors.toList());
//    return medicoListadoDTOS;

    return medicos.stream()
        .map(MedicoListadoDTO::new)
        .toList();
  }

  public Page<MedicoListadoDTO> listarPaginado(Pageable pageable) {
    // Fetch the page of doctors from the repository. findAll(pageable) returns a page of entities (Medic)
    Page<Medico> medicosPage = medicoRepository.findAll(pageable);

    // Map the doctors page to a DTOs page
    return medicosPage.map(medico -> new MedicoListadoDTO(
        medico.getId(),
        medico.getNombre(),
        medico.getEspecialidad().toString(),
        medico.getDocumento(),
        medico.getEmail()
    ));
  }

  public Page<MedicoListadoDTO> listarPaginadoEquivalente(Pageable pageable) {
    Page<Medico> medicos = medicoRepository.findAll(pageable);
    return medicos.map(MedicoListadoDTO::new);
  }


  // update
//  public void actualizarMedico(Long id, MedicoDTO medicoDTO) {
//
//    // Find the medico by ID
//    Medico medico = medicoRepository.findById(id)
//        .orElseThrow(() -> new RuntimeException("Medico not found"));
//
//    // Copy the updated fields from the DTO to the medico entity
//    medico.setNombre(medicoDTO.nombre());
//    medico.setEspecialidad(medicoDTO.especialidad());
//    medico.setDocumento(medicoDTO.documento());
//    medico.setEmail(medicoDTO.email());
//  }

}
