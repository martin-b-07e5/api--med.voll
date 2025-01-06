package med.voll.api.entity;


public record MedicoListadoSimpleDTO(
    Long id,
    String nombre,
    Boolean inactivo
) {
  // constructor
  public MedicoListadoSimpleDTO(Medico medico) {
    this(medico.getId(), medico.getNombre(), medico.getInactivo());
  }

}
