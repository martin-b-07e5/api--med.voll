package med.voll.api.Record;

public record DireccionDTO(
    String calle,
    Integer numero,
    String piso,
    String ciudad,
    String provincia,
    String pais
) {
}
