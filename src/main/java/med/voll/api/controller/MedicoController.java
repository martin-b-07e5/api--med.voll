package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.entity.MedicoDTO;
import med.voll.api.entity.MedicoListadoDTO;
import med.voll.api.service.MedicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


// In the controller, simply delegate the task to the service.

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  private static final Logger logger = LoggerFactory.getLogger(MedicoController.class);

  private final MedicoService medicoService;

  // Constructor-based dependency injection
  @Autowired
  public MedicoController(MedicoService medicoService) {
    this.medicoService = medicoService;

  }

  @PostMapping
  public void agregarMedico(@RequestBody @Valid MedicoDTO medicoDTO) {
    medicoService.registrarMedico(medicoDTO);
  }

  // http://localhost:8080/medicos?page=0&size=2&sort=nombre,asc
  @GetMapping
  public Page<MedicoListadoDTO> listarMedicosParcial(@PageableDefault(size = 2, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {

    // Print by console (log)
    logger.info("Página solicitada: {}", pageable.getPageNumber());
    logger.info("Tamaño de página: {}", pageable.getPageSize());
    logger.info("Ordenamiento: {}", pageable.getSort());

    return medicoService.listarMedicosParcial(pageable);
  }

}
