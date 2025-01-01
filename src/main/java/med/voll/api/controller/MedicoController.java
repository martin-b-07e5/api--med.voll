package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.entity.MedicoDTO;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

