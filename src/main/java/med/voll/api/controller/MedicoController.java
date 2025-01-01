package med.voll.api.controller;

import med.voll.api.Record.DatosRegistroMedicoDTO;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

  private final MedicoService medicoService;

  // constructor dependency injection
  @Autowired
  public MedicoController(MedicoService medicoService) {
    this.medicoService = medicoService;
  }

  // methods
  @PostMapping
  public void agregarMedico(@RequestBody DatosRegistroMedicoDTO datosRegistroMedicoDTO) {
    medicoService.registrarMedico(datosRegistroMedicoDTO);
    // medicoRepository.save(new Medico(datosRegistroMedicoDTO));  // before
  }

}

