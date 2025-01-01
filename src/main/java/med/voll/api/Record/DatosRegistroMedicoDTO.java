package med.voll.api.Record;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosRegistroMedicoDTO(
    String nombre,
    String email,
    String documento,
    EspecialidadEnum especialidad,
    DireccionDTO direccion
) {

}
