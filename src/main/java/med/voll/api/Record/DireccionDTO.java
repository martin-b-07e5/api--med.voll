package med.voll.api.Record;

public record DireccionDTO(
    String calle,
    String numero,
    String piso,
    String ciudad,
    String provincia,
    String pais
) {
}
