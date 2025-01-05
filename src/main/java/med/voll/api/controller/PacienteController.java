package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.entity.Paciente;
import med.voll.api.entity.PacienteDTO;
import med.voll.api.entity.PacienteListadoDTO;
import med.voll.api.service.PacienteService;
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
@RequestMapping("/pacientes")
public class PacienteController {

  private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

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


  // http://localhost:8080/pacientes/listar
  @GetMapping("/listar")
  public List<Paciente> listarPacientes() {
    return pacienteService.listarPacientes();
  }

  // http://localhost:8080/pacientes/listarDTO
  @GetMapping("/listarDTO")
  public List<PacienteListadoDTO> listarDTO() {
    return pacienteService.listarDTO();
  }


  // http://localhost:8080/pacientes/listarPaginado
  // http://localhost:8080/pacientes/listarPaginado?page=0&size=3&sort=nombre,asc
  @GetMapping("/listarPaginado")
  public Page<PacienteListadoDTO> listarPaginado(
      @PageableDefault(size = 3, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {

    return pacienteService.listarPaginado(pageable);
  }


}

