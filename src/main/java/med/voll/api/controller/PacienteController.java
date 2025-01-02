package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.entity.PacienteDTO;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// In the controller, simply delegate the task to the service.

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

  private final PacienteService pacienteService;

  // Constructor-based dependency injection
  @Autowired
  public PacienteController(PacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  @PostMapping
  public void agregarPaciente(@RequestBody @Valid PacienteDTO pacienteDTO) {
    pacienteService.registrarPaciente(pacienteDTO);
  }

}

