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
    String nombre,
    String especialidad,
    String documento,
    String email
) {
}
