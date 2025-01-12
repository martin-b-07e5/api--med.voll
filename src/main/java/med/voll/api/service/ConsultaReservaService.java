package med.voll.api.service;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaDatosReservaDTO;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaReservaService {

  @Autowired
  private MedicoRepository medicoRepository;
  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private ConsultaRepository consultaRepository;


  public Consulta reservar(@Valid ConsultaDatosReservaDTO datos) {
    System.out.println("[ConsultaReservaService][reservar]Reservando consulta: " + datos);

    var medico = medicoRepository.findById(datos.idMedico()).orElseThrow(() -> new RuntimeException("Medico no encontrado"));
    var paciente = pacienteRepository.findById(datos.idPaciente()).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    var fecha = datos.fecha();
    var consulta = new Consulta(null, medico, paciente, fecha);

    System.out.println("\nmedico: " + medico);
    System.out.println("paciente: " + paciente);
    System.out.println("fecha: " + fecha);

    System.out.println("\nconsulta.getIdConsulta()): " + consulta.getIdConsulta());
    System.out.println("consulta.getMedico().getIdMedico()): " + consulta.getMedico().getIdMedico());
    System.out.println("consulta.getPaciente().getIdPaciente()): " + consulta.getPaciente().getIdPaciente());
    System.out.println("consulta.getFecha()): " + consulta.getFecha());

    consultaRepository.save(consulta);
    return consulta;
  }


}
