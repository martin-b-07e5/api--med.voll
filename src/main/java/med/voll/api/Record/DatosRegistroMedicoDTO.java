package med.voll.api.Record;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosRegistroMedicoDTO(
    String nombre,
    String email,
    String documento,
    EspecialidadEnum especialidad,
    // Aquí es un objeto, no una lista (Acepta "direccion" y "dirección")
    @JsonAlias("dirección") DireccionDTO direccion
) {

}
