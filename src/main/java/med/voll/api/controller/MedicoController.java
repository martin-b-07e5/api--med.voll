package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.entity.Medico;
import med.voll.api.entity.MedicoDTO;
import med.voll.api.entity.MedicoListadoDTO;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// In the controller, simply delegate the task to the service.

@RestController
@RequestMapping("/medicos")
public class MedicoController {

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


//  @GetMapping
//  public List<MedicoListadoDTO> listarMedicosParcial() {
//    // Implementación para listar medicos parcialmente (solo algunos campos)
//    return medicoService.listarMedicosParcial();
//  }


  //  http://localhost:8080/medicos?page=0&size=2&sort=nombre,asc
  @GetMapping
  public Page<MedicoListadoDTO> listarMedicosParcial(Pageable pageable) {
    // We limit to 2 records per page and ascending order by name.
    Pageable pageableWithLimit = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Order.asc("nombre")));
    return medicoService.listarMedicosParcial(pageableWithLimit);
  }


}

