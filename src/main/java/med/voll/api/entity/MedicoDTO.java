package med.voll.api.entity;

public record MedicoDTO(
    String nombre,
    String email,
    String documento,
    EspecialidadEnum especialidad,
    DireccionDTO direccion
) {

}
