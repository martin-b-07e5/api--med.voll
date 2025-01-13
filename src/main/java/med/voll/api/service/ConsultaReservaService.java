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
    validarConsultaEnElMismoDia(datos.idPaciente(), datos.fecha());

    // Buscar o seleccionar médico disponible
    Medico medico = obtenerMedico(datos);

    // Validar que el médico tenga disponibilidad en la hora solicitada
    validarDisponibilidadMedico(medico, datos.fecha());

    // Buscar al paciente
    Paciente paciente = obtenerPaciente(datos.idPaciente());

    // Crear y guardar la consulta
    return crearConsulta(medico, paciente, datos.fecha());
  }

  // Validar que el paciente no tenga otra consulta el mismo día
  private void validarConsultaEnElMismoDia(Long idPaciente, LocalDateTime fecha) {
    boolean consultaEnElMismoDia = consultaRepository.existsConsultaEnElMismoDia(idPaciente, fecha);
    if (consultaEnElMismoDia) {
      throw new IllegalArgumentException("The patient already has a consultation scheduled on the same day.");
    }
  }

  // Obtener el médico, ya sea uno proporcionado o uno aleatorio disponible
  private Medico obtenerMedico(ConsultaDatosReservaDTO datos) {
    if (datos.idMedico() != null) {
      return medicoRepository.findById(datos.idMedico())
          .orElseThrow(() -> new MedicoNotFoundException(datos.idMedico()));
    }
    return seleccionarMedicoAleatorioDisponible(datos.fecha());
  }

  // Validar que el médico no tenga una consulta en la misma hora
  private void validarDisponibilidadMedico(Medico medico, LocalDateTime fechaHora) {
    boolean consultaExistente = consultaRepository.existsConsultaConConflicto(medico.getIdMedico(), fechaHora, fechaHora.plusHours(1));
    if (consultaExistente) {
      throw new IllegalArgumentException("The doctor already has a consultation scheduled at this time.");
    }
  }

  // Obtener al paciente desde la base de datos
  private Paciente obtenerPaciente(Long idPaciente) {
    return pacienteRepository.findById(idPaciente)
        .orElseThrow(() -> new PatientNotFoundException(idPaciente));
  }

  // Crear y guardar la consulta
  private Consulta crearConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
    Consulta consulta = new Consulta(null, medico, paciente, fecha);
    consultaRepository.save(consulta);
    return consulta;
  }

  // Método para seleccionar un médico aleatorio disponible
  private Medico seleccionarMedicoAleatorioDisponible(LocalDateTime fechaHora) {
    List<Medico> medicosDisponibles = medicoRepository.findMedicosDisponibles(fechaHora);

    if (medicosDisponibles.isEmpty()) {
      throw new NoAvailableDoctorException("No doctors are available at the chosen time.");
    }

    Random random = new Random();
    return medicosDisponibles.get(random.nextInt(medicosDisponibles.size()));
  }

}
