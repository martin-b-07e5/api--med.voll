package med.voll.api.service;

import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
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
    return medicosPage.map(MedicoListadoDTO::new);
  }

  public Page<MedicoListadoDTO> listarPaginadoEquivalente(Pageable pageable) {
    Page<Medico> medicos = medicoRepository.findAll(pageable);
    return medicos.map(MedicoListadoDTO::new);
  }


  // create
  @Transactional
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

  @Transactional(readOnly = true)
  public Medico getMedicoById(Long id) {
    return medicoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + id));
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
    Medico medico = medicoRepository.getReferenceById(id);
    medicoRepository.delete(medico);
  }

//  @Transactional
//  public void excluirMedico(Long id, MedicoExclusionDTO medicoExclusionDTO) {
//
//    // Check if the medico exists before trying to exclude it
//    if (!medicoRepository.existsById(id)) {
//      throw new EntityNotFoundException("Médico con ID " + id + " no encontrado.");
//    }
//
//    Medico medico = medicoRepository.getReferenceById(id);
//    medico.setInactivo(medicoExclusionDTO.inactivo());
//  }

  public void excluirMedicoPojo(Long id) {
    var medico = medicoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Médico no encontrado"));
    medico.setInactivo(true); // Marca al médico como inactivo
    medicoRepository.save(medico); // Guarda los cambios en la base de datos
  }

  public void excluirMedicoDTO(Long id, MedicoExclusionDTO medicoExclusionDTO) {
    Medico medico = medicoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
    medico.setInactivo(medicoExclusionDTO.inactivo());
    medicoRepository.save(medico);
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
