package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.Direccion;

public record MedicoUpdateDTO(
    @NotNull Long idMedico,
    String nombre,
    String documento,
    @Valid Direccion direccion
) {

  // Constructor to map Medico entity to MedicoUpdateDTO
  public MedicoUpdateDTO(Medico medico) {
    this(medico.getIdMedico(), medico.getNombre(), medico.getDocumento(), medico.getDireccion());
  }

}
