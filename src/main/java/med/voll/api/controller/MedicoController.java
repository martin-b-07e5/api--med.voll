package med.voll.api.controller;

import med.voll.api.Record.DatosRegistroMedicoDTO;
import med.voll.api.entity.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicoController {

  // dependency injection
  @Autowired
  MedicoRepository medicoRepository;

  @PostMapping("/medicos")
  public void agregarMedico(@RequestBody DatosRegistroMedicoDTO datosRegistroMedicoDTO) {
    medicoRepository.save(new Medico(datosRegistroMedicoDTO));
  }

}
