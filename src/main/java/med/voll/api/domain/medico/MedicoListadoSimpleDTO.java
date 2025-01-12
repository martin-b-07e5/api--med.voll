package med.voll.api.domain.medico;


public record MedicoListadoSimpleDTO(
    Long idMedico,
    String nombre,
    Boolean inactivo
) {

  // Constructor to map Medico entity to MedicoListadoSimpleDTO
  public MedicoListadoSimpleDTO(Medico medico) {
    this(medico.getIdMedico(), medico.getNombre(), medico.getInactivo());
  }

}
