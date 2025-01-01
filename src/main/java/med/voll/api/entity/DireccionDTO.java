package med.voll.api.entity;

public record DireccionDTO(
    String calle,
    Integer numero,
    String piso,
    String ciudad,
    String provincia,
    String pais
) {
}
