package med.voll.api.entity;

/* Consideraciones
 * nombre
 * documento
 * email

 * Reglas de negocios
 * Ordenados ascendente
 * paginado, máximo 10 registros x pagína
 */

public record PacienteListadoDTO(
    String nombre,
    String documento,
    String email
) {
  public PacienteListadoDTO(Paciente paciente) {
    this(paciente.getNombre(), paciente.getDocumento(), paciente.getEmail());
  }
}
