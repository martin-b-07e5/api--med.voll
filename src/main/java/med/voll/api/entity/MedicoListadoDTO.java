package med.voll.api.entity;

public record MedicoListadoDTO(
    Long id,
    String nombre,
    String especialidad,
    String documento,
    String email,
    Boolean inactivo
) {
  // constructor
  public MedicoListadoDTO(Medico medico) {
    this(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(), medico.getDocumento(),
        medico.getEmail(), medico.getInactivo());
  }

}
