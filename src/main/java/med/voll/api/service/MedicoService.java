package med.voll.api.service;

import med.voll.api.mapper.MedicoValidatorMapper;
import med.voll.api.entity.MedicoDTO;
import med.voll.api.entity.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

//  public List<Medico> listarMedicos() {
//    return medicoRepository.findAll();
//  }

  public List<Medico> listarMedicos() {
    List<Medico> medicos = medicoRepository.findAll();

    // Verificar si la dirección se carga correctamente
    for (Medico medico : medicos) {
      System.out.println("Medico: " + medico);
    }

    return medicos;
  }


}
