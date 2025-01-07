package med.voll.api.service;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.exception.MedicoNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import med.voll.api.controller.MedicoController;
import med.voll.api.entity.*;
import med.voll.api.mapper.MedicoValidatorMapper;
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
    return medicos.stream()
        .map(MedicoListadoDTO::new)
        .toList();
  }

  public Page<MedicoListadoDTO> listarPaginado(Pageable pageable) {
    // Fetch the page of doctors from the repository. findAll(pageable) returns a page of entities (Medic)
    Page<Medico> medicosPage = medicoRepository.findAll(pageable);

    // Map the doctors page to a DTOs page
    return medicosPage.map(MedicoListadoDTO::new);
  }

  public Page<MedicoListadoDTO> listarPaginadoEquivalente(Pageable pageable) {
    Page<Medico> medicos = medicoRepository.findAll(pageable);
    return medicos.map(MedicoListadoDTO::new);
  }


  // create
  @Transactional
  public Medico addMedico(MedicoDTO medicoDTO) {

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
    return medico;
  }

  @Transactional(readOnly = true)
  public Medico getMedicoById(Long id) {
    return medicoRepository.findById(id)
        .orElseThrow(() -> new MedicoNotFoundException("Médico no encontrado con ID: " + id));
  }


  // update findById
  @Transactional
  public void updateMedico_findById(MedicoUpdateDTO medicoUpdateDTO) {

    // Get the medico from the DB
    Medico medico = medicoRepository.findById(medicoUpdateDTO.id())
        .orElseThrow(() -> new EntityNotFoundException("Médico no encontrado"));

    // Update only allowed fields
    if (medicoUpdateDTO.nombre() != null) {
      medico.setNombre(medicoUpdateDTO.nombre());
    }
    if (medicoUpdateDTO.documento() != null) {
      medico.setDocumento(medicoUpdateDTO.documento());
    }
    if (medicoUpdateDTO.direccion() != null) {
      medico.setDireccion(medicoUpdateDTO.direccion());
    }

    // It is not necessary to call save() as the transaction will automatically save the changes.
    medicoRepository.save(medico);
  }

  // update getReferenceById
  @Transactional
  public void updateMedico_getReferenceById(MedicoUpdateDTO medicoUpdateDTO) {

    // Get the medico from the DB
    Medico medico = medicoRepository.getReferenceById(medicoUpdateDTO.id());

    // Update only allowed fields
    if (medicoUpdateDTO.nombre() != null) {
      medico.setNombre(medicoUpdateDTO.nombre());
    }
    if (medicoUpdateDTO.documento() != null) {
      medico.setDocumento(medicoUpdateDTO.documento());
    }
    if (medicoUpdateDTO.direccion() != null) {
      medico.setDireccion(medicoUpdateDTO.direccion());
    }

    // It is not necessary to call save() as the transaction will automatically save the changes.
//    medicoRepository.save(medico);
  }


  // delete
  @Transactional
  public void eliminarMedicoHard(Long id) {
//    Medico medico = medicoRepository.getReferenceById(id);
    if (!medicoRepository.existsById(id)) {
      throw new MedicoNotFoundException(id);
    }
    medicoRepository.deleteById(id);
  }

  public String excluirMedicoPojo(Long id) {
    Medico medico = medicoRepository.findById(id)
        .orElseThrow(() -> new MedicoNotFoundException(id));  // Custom exception

    if (medico.getInactivo()) {
      throw new IllegalStateException("El médico ya está marcado como inactivo");
    }

    medico.setInactivo(true); // Mark the doctor as inactive
    medicoRepository.save(medico); // Save changes to the database

    return "Médico con ID " + id + " marcado como inactivo correctamente.";
  }

  public String excluirMedicoDTO(Long id, MedicoExclusionDTO medicoExclusionDTO) {
    Medico medico = medicoRepository.findById(id)
        .orElseThrow(() -> new MedicoNotFoundException("Médico no encontrado"));

    if (medico.getInactivo()) {
      throw new IllegalStateException("El médico ya está marcado como inactivo");
    }

    medico.setInactivo(medicoExclusionDTO.inactivo());
    medicoRepository.save(medico);

    return "Médico con ID " + id + " marcado como inactivo correctamente.";
  }


  //------------------------------------
  // retorna entidades
  @Transactional(readOnly = true)
  public List<Medico> listarMedicosInactivosEntidades() {
    return medicoRepository.findByInactivoTrue();
  }

  // retorna DTOs
  @Transactional(readOnly = true)
  public List<MedicoListadoSimpleDTO> listarMedicosInactivosDTO() {
    return medicoRepository.findByInactivoTrue()
        .stream()
        .map(MedicoListadoSimpleDTO::new)
        .toList();
  }

  // return DTOs paginados
  @Transactional(readOnly = true)
  public Page<MedicoListadoSimpleDTO> listarMedicosActivosDTO(Pageable page) {
    Page<Medico> medicosPage = medicoRepository.findByInactivoFalse(page);
    return medicosPage
        .map(MedicoListadoSimpleDTO::new);
  }


  @Transactional(readOnly = true)
  public List<MedicoListadoSimpleDTO> listarMedicosPorEstado(boolean inactivo) {
    return medicoRepository.findByInactivo(inactivo)
        .stream()
        .map(MedicoListadoSimpleDTO::new)
        .toList();
  }


}
