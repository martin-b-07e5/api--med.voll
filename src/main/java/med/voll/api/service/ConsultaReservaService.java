package med.voll.api.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaDatosReservaDTO;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.exception.MedicoNotFoundException;
import med.voll.api.exception.NoAvailableDoctorException;
import med.voll.api.exception.PatientNotFoundException;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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

    // Validar que el paciente no tenga otra consulta el mismo día
    boolean consultaEnElMismoDia = consultaRepository.existsConsultaEnElMismoDia(
        datos.idPaciente(),
        datos.fecha()
    );

    if (consultaEnElMismoDia) {
      throw new IllegalArgumentException("The patient already has a consultation scheduled on the same day.");
    }

    // Lógica existente para buscar o seleccionar médico, validar disponibilidad, etc.
    Medico medico = null;
    if (datos.idMedico() != null) {
      medico = medicoRepository.findById(datos.idMedico())
          .orElseThrow(() -> new MedicoNotFoundException(datos.idMedico()));
    }
    if (medico == null) {
      medico = seleccionarMedicoAleatorioDisponible(datos.fecha());
    }

    // Validar si el médico tiene una consulta en la misma fecha y hora
    boolean consultaExistente = consultaRepository.existsConsultaConConflicto(
        medico.getIdMedico(),
        datos.fecha(),
        datos.fecha().plusHours(1)
    );

    if (consultaExistente) {
      throw new IllegalArgumentException("The doctor already has a consultation scheduled at this time.");
    }

    // Buscar al paciente
    Paciente paciente = pacienteRepository.findById(datos.idPaciente())
        .orElseThrow(() -> new PatientNotFoundException(datos.idPaciente()));

    // Crear y guardar la consulta
    Consulta consulta = new Consulta(null, medico, paciente, datos.fecha());
    consultaRepository.save(consulta);

    return consulta;
  }

  // Method to select a random physician available at the date and time provided.
  private Medico seleccionarMedicoAleatorioDisponible(LocalDateTime fechaHora) {

    // Search for physicians available at that date/time
    List<Medico> medicosDisponibles = medicoRepository.findMedicosDisponibles(fechaHora);

    // Si no hay médicos disponibles, lanzar una excepción o devolver un valor predeterminado
    if (medicosDisponibles.isEmpty()) {
      throw new NoAvailableDoctorException("No doctors are available at the chosen time.");
    }

    // Seleccionar aleatoriamente un médico de los disponibles
    Random random = new Random();
    return medicosDisponibles.get(random.nextInt(medicosDisponibles.size()));
  }


}
