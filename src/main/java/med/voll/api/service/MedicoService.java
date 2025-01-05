package med.voll.api.service;

import med.voll.api.entity.MedicoListadoDTO;
import med.voll.api.mapper.MedicoValidatorMapper;
import med.voll.api.entity.MedicoDTO;
import med.voll.api.entity.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/// Creates a service to encapsulate business logic and handle the conversion of the DTO to the Medico entity before persisting it.

@Service
public class MedicoService {

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  MedicoValidatorMapper medicoValidatorMapper;


//  private final MedicoValidatorMapper medicoValidatorMapper;
//  private final MedicoRepository medicoRepository;

  // Constructor-based dependency injection
//  public MedicoService(MedicoValidatorMapper medicoValidatorMapper, MedicoRepository medicoRepository) {
//    this.medicoValidatorMapper = medicoValidatorMapper;
//    this.medicoRepository = medicoRepository;
//  }

//  public MedicoService(MedicoRepository medicoRepository) {
//    this.medicoRepository = medicoRepository;
//  }

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


}
