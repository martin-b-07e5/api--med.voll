package med.voll.api.controller;

import med.voll.api.Record.DatosRegistroMedicoDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicoController {

  @PostMapping("/medicos")
  public String agregarMedico(@RequestBody DatosRegistroMedicoDTO datosRegistroMedicoDTO) {
    System.out.println("nombre: " + datosRegistroMedicoDTO.nombre());
    System.out.println("email: " + datosRegistroMedicoDTO.email());
    System.out.println("documento: " + datosRegistroMedicoDTO.documento());
    System.out.println("especialidad: " + datosRegistroMedicoDTO.especialidad());
    System.out.println("Address");
    System.out.println("  calle: " + datosRegistroMedicoDTO.calle());
    System.out.println("  numero: " + datosRegistroMedicoDTO.direccion().numero());
    System.out.println("  piso: " + datosRegistroMedicoDTO.direccion().piso());
    System.out.println("  ciudad: " + datosRegistroMedicoDTO.direccion().ciudad());
    System.out.println("  provincia: " + datosRegistroMedicoDTO.direccion().provincia());
    System.out.println("  pais: " + datosRegistroMedicoDTO.direccion().pais());
    System.out.println(datosRegistroMedicoDTO);
    return "Medico agregado...";
  }

}
