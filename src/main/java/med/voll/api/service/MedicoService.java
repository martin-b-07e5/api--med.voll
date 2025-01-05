package med.voll.api.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import med.voll.api.controller.MedicoController;
import med.voll.api.entity.MedicoListadoDTO;
import med.voll.api.entity.MedicoListadoDTOActualizar;
import med.voll.api.mapper.MedicoValidatorMapper;
import med.voll.api.entity.MedicoDTO;
import med.voll.api.entity.Medico;
import med.voll.api.repository.MedicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/// Creates a service to encapsulate business logic and handle the conversion of the DTO to the Medico entity before persisting it.

@Service
public class MedicoService {

  private static final Logger logger = LoggerFactory.getLogger(MedicoController.class);

  @Autowired
  MedicoRepository medicoRepository;

  @Autowired
  MedicoValidatorMapper medicoValidatorMapper;


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

    // Validate the DTO before passing it to the mapper for conversion
    Medico medico = medicoValidatorMapper.toEntity(medicoDTO);

    // If the DTO is valid, it is saved in the database.
    medicoRepository.save(medico);
  }

  // update
  public void updateMedico(MedicoListadoDTOActualizar medicoListadoDTOActualizar) {

    // Get the medico from the DB
    Medico medico = medicoRepository.findById(medicoListadoDTOActualizar.id())
        .orElseThrow(() -> new EntityNotFoundException("Médico no encontrado"));

    // Update only allowed fields
    if (medicoListadoDTOActualizar.nombre() != null) {
      medico.setNombre(medicoListadoDTOActualizar.nombre());
    }
    if (medicoListadoDTOActualizar.documento() != null) {
      medico.setDocumento(medicoListadoDTOActualizar.documento());
    }
    if (medicoListadoDTOActualizar.direccion() != null) {
      medico.setDireccion(medicoListadoDTOActualizar.direccion());
    }

    medicoRepository.save(medico);
  }

  @Transactional
  public void updateMedico2(MedicoListadoDTOActualizar medicoListadoDTOActualizar) {

    // Get the medico from the DB
    Medico medico = medicoRepository.getReferenceById(medicoListadoDTOActualizar.id());

    // Update only allowed fields
    if (medicoListadoDTOActualizar.nombre() != null) {
      medico.setNombre(medicoListadoDTOActualizar.nombre());
    }
    if (medicoListadoDTOActualizar.documento() != null) {
      medico.setDocumento(medicoListadoDTOActualizar.documento());
    }
    if (medicoListadoDTOActualizar.direccion() != null) {
      medico.setDireccion(medicoListadoDTOActualizar.direccion());
    }

    // Save is not necessary if the entity is already managed
    // However, it can be added explicitly for clarity
    // It is not necessary to call save() as the transaction will automatically save the changes.
    // it needs @Transactional, so I can comment the next line.
//    medicoRepository.save(medico);
  }

}
