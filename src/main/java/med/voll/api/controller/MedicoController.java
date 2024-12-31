package med.voll.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicoController {

  @PostMapping("/medicos")
  public String agregarMedico(@RequestBody String parametro) {
    System.out.println("parametro: " + parametro);
    return "Medico agregado...";
  }


}
