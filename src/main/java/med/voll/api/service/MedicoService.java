package med.voll.api.service;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.medico.*;
import med.voll.api.exception.MedicoNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import med.voll.api.controller.MedicoController;
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


  /// read --------------------------------------------------------------------

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

  @Transactional(readOnly = true)
  // retorna entidades
  public List<Medico> listarMedicosInactivosEntidades() {
    return medicoRepository.findByInactivoTrue();
  }

  @Transactional(readOnly = true)
  // retorna DTOs
  public List<MedicoListadoSimpleDTO> listarMedicosInactivosDTO() {
    return medicoRepository.findByInactivoTrue()
        .stream()
        .map(MedicoListadoSimpleDTO::new)
        .toList();
  }

  @Transactional(readOnly = true)
  // return DTOs paginados
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

  @Transactional(readOnly = true)
  public Medico getMedicoById(Long idMedico) {
    return medicoRepository.findById(idMedico)
        .orElseThrow(() -> new MedicoNotFoundException("Médico no encontrado con ID: " + idMedico));
  }

  /// create ------------------------------------------------------------------

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


  /// update ------------------------------------------------------------------

  @Transactional
  // update findById
  public void updateMedico_findById(MedicoUpdateDTO medicoUpdateDTO) {

    // Get the medico from the DB
    Medico medico = medicoRepository.findById(medicoUpdateDTO.idMedico())
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

  @Transactional
  // update getReferenceById
  public void updateMedico_getReferenceById(MedicoUpdateDTO medicoUpdateDTO) {

    // Get the medico from the DB
    Medico medico = medicoRepository.getReferenceById(medicoUpdateDTO.idMedico());

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

  public String excluirMedicoPojo(Long idMedico) {
    Medico medico = medicoRepository.findById(idMedico)
        .orElseThrow(() -> new MedicoNotFoundException(idMedico));  // Custom exception

    if (medico.getInactivo()) {
      throw new IllegalStateException("El médico ya está marcado como inactivo");
    }

    medico.setInactivo(true); // Mark the doctor as inactive
    medicoRepository.save(medico); // Save changes to the database

    return "Médico con ID " + idMedico + " marcado como inactivo correctamente.";
  }

  public void excluirMedicoDTO(Long idMedico, MedicoExclusionDTO medicoExclusionDTO) {
    Medico medico = medicoRepository.findById(idMedico)
        .orElseThrow(() -> new MedicoNotFoundException(idMedico));

    if (medicoExclusionDTO.inactivo()) {
      if (medico.getInactivo()) {
        throw new IllegalStateException("El médico ya está marcado como inactivo");
      }
      medico.setInactivo(true);  // Marcar como inactivo
    } else {
      if (!medico.getInactivo()) {
        throw new IllegalStateException("El médico ya está marcado como activo");
      }
      medico.setInactivo(false);  // Marcar como activo
    }

    medicoRepository.save(medico);  // Guardar cambios en la base de datos
  }


  /// delete ------------------------------------------------------------------

  @Transactional
  public String eliminarMedicoHard(Long idMedico) {
    if (!medicoRepository.existsById(idMedico)) {
      throw new MedicoNotFoundException(idMedico);  // Throw an exception if not found
    }
    // We eliminate the doctor
    medicoRepository.deleteById(idMedico);

    // Success message
    return "Médico con ID " + idMedico + " eliminado exitosamente.";

  }

}
