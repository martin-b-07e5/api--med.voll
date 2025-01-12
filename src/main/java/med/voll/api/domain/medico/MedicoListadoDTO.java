package med.voll.api.domain.medico;

public record MedicoListadoDTO(
    Long idMedico,
    String nombre,
    String especialidad,
    String documento,
    String email,
    Boolean inactivo
) {

  // constructor to map Medico entity to MedicoListadoDTO
  public MedicoListadoDTO(Medico medico) {
    this(medico.getIdMedico(), medico.getNombre(), medico.getEspecialidad().toString(), medico.getDocumento(),
        medico.getEmail(), medico.getInactivo());
  }

}
