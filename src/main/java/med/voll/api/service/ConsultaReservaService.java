package med.voll.api.service;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaDatosReservaDTO;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultaReservaService {

  @Autowired
  private MedicoRepository medicoRepository;
  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private ConsultaRepository consultaRepository;


  public Consulta reservar(@Valid ConsultaDatosReservaDTO datos) {

    var horaInicio = 7; // 07:00 hs
    var horaFin = 19;   // 19:00 hs

    // Validar el horario de la consulta
    if (datos.fecha().getHour() < horaInicio || datos.fecha().getHour() >= horaFin) {
      throw new IllegalArgumentException("La hora de la consulta debe estar entre las 07:00 y las 19:00.");
    }

    var medico = medicoRepository.findById(datos.idMedico()).orElseThrow(() -> new RuntimeException("Medico no encontrado"));
    var paciente = pacienteRepository.findById(datos.idPaciente()).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    var fecha = datos.fecha();
    var consulta = new Consulta(null, medico, paciente, fecha);

    printTemp(datos, medico, paciente, fecha, consulta);

    consultaRepository.save(consulta);
    return consulta;
  }

  private void printTemp(@Valid ConsultaDatosReservaDTO datos, Medico medico, Paciente paciente, LocalDateTime fecha, Consulta consulta) {
    System.out.println("[ConsultaReservaService][reservar]Reservando consulta: " + datos);
    System.out.println("\nmedico: " + medico);
    System.out.println("paciente: " + paciente);
    System.out.println("fecha: " + fecha);
    System.out.println("\nconsulta.getIdConsulta()): " + consulta.getIdConsulta());
    System.out.println("consulta.getMedico().getIdMedico()): " + consulta.getMedico().getIdMedico());
    System.out.println("consulta.getPaciente().getIdPaciente()): " + consulta.getPaciente().getIdPaciente());
    System.out.println("consulta.getFecha()): " + consulta.getFecha());
  }


}
