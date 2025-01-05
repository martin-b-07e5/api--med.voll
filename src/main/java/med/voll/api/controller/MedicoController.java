package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.entity.Medico;
import med.voll.api.entity.MedicoDTO;
import med.voll.api.entity.MedicoListadoDTO;
import med.voll.api.entity.MedicoListadoDTOActualizar;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.service.MedicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// In the controller, simply delegate the task to the service.

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  private static final Logger logger = LoggerFactory.getLogger(MedicoController.class);

  private final MedicoService medicoService;
  private final MedicoRepository medicoRepository;

  // Constructor-based dependency injection
  @Autowired
  public MedicoController(MedicoService medicoService,
                          MedicoRepository medicoRepository) {
    this.medicoService = medicoService;

    this.medicoRepository = medicoRepository;
  }

  // read
  //  http://localhost:8080/medicos/listar
  @GetMapping("/listar")
  public List<Medico> listarMedicos() {
    return medicoService.listarMedicos();
  }

  // http://localhost:8080/medicos/listarDTO
  @GetMapping("/listarDTO")
  public List<MedicoListadoDTO> listarDTO() {
    return medicoService.listarDTO();
  }


  // http://localhost:8080/medicos
  // http://localhost:8080/medicos?page=0&size=2&sort=nombre,asc
  @GetMapping
  public Page<MedicoListadoDTO> listarPaginado(
      @PageableDefault(size = 2, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {

    // Print by console (log)
    logger.info("Página solicitada: {}", pageable.getPageNumber());
    logger.info("Tamaño de página: {}", pageable.getPageSize());
    logger.info("Ordenamiento: {}", pageable.getSort());

    return medicoService.listarPaginado(pageable);
  }

  // http://localhost:8080/medicos/equivalente
  // http://localhost:8080/medicos/equivalente?page=0&size=2&sort=nombre,asc
  @GetMapping("/equivalente")
  public Page<MedicoListadoDTO> listarPaginadoEquivalente(
      @PageableDefault(size = 2, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
    return medicoService.listarPaginadoEquivalente(pageable);
  }


  // create
  @PostMapping
  public void agregarMedico(@RequestBody @Valid MedicoDTO medicoDTO) {
    medicoService.registrarMedico(medicoDTO);
  }

  // update
  @PutMapping
  @Transactional
  public void actualizarMedico(@RequestBody @Valid MedicoListadoDTOActualizar datosActualizarMedico) {
    Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
    logger.info("Medico: {}", medico);
    logger.info("-------------controller1-------------------");
    medico.actualizarDatos(datosActualizarMedico);
    logger.info("-------------controller1-------------------");
    logger.info("datosActualizarMedico: {}", datosActualizarMedico);
    logger.info("datosActualizarMedico.id(): {}", datosActualizarMedico.id());
    logger.info("datosActualizarMedico.direccion: {}", datosActualizarMedico.direccion());
  }


}
