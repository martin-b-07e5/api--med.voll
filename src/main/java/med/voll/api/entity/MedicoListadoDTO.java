package med.voll.api.entity;

/* Consideraciones
 * nombre
 * especialidad
 * documento
 * email

 * Reglas de negocios
 * Ordenados ascendente
 * paginado, máximo 10 registros x pagína
 */

public record MedicoListadoDTO(
    Long id,
    String nombre,
    String especialidad,
    String documento,
    String email
) {
  // constructor
  public MedicoListadoDTO(Medico medico) {
    this(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(), medico.getDocumento(), medico.getEmail());
  }

}
