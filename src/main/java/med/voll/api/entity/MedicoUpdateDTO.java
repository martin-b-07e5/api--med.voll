package med.voll.api.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record MedicoUpdateDTO(
    @NotNull Long id,
    String nombre,
    String documento,
    @Valid Direccion direccion
) {

  // Constructor to map Medico entity to MedicoUpdateDTO
  public MedicoUpdateDTO(Medico medico) {
    this(medico.getId(), medico.getNombre(), medico.getDocumento(), medico.getDireccion());
  }

}
