package med.voll.api.service;

import jakarta.transaction.Transactional;
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

  @Transactional
  public Consulta reservar(@Valid ConsultaDatosReservaDTO datos) {

    // Check to see if the doctor already has a consultation in that time range.
    boolean consultaExistente = consultaRepository.existsByMedico_IdMedicoAndFechaBetween(
        datos.idMedico(),
        datos.fecha(),
        datos.fecha().plusHours(1)
    );

    if (consultaExistente) {
      throw new IllegalArgumentException("The doctor already has a consultation scheduled at this time.");
    }

    var medico = medicoRepository.findById(datos.idMedico()).orElseThrow(() -> new RuntimeException("Doctor not found"));
    var paciente = pacienteRepository.findById(datos.idPaciente()).orElseThrow(() -> new RuntimeException("Patient not found"));
    var fecha = datos.fecha();
    var consulta = new Consulta(null, medico, paciente, fecha);

    printTemp(datos, medico, paciente, fecha, consulta);

    consultaRepository.save(consulta);
    return consulta;
  }

  private void printTemp(@Valid ConsultaDatosReservaDTO datos, Medico medico, Paciente paciente, LocalDateTime fecha, Consulta consulta) {
    System.out.println("[ConsultaReservaService][reservar]Make an appointment: " + datos);
    System.out.println("\nmedico: " + medico);
    System.out.println("paciente: " + paciente);
    System.out.println("fecha: " + fecha);
    System.out.println("\nconsulta.getIdConsulta()): " + consulta.getIdConsulta());
    System.out.println("consulta.getMedico().getIdMedico()): " + consulta.getMedico().getIdMedico());
    System.out.println("consulta.getPaciente().getIdPaciente()): " + consulta.getPaciente().getIdPaciente());
    System.out.println("consulta.getFecha()): " + consulta.getFecha());
  }


}
