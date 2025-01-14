package med.voll.api.domain.consulta;

public record ConsultaListadoDTO(
    Long idConsulta,
    Long idMedico,
    Long idPaciente
) {
  // Constructor to map Consulta entity to ConsultaListadoDTO
  public ConsultaListadoDTO(Consulta consulta) {
    this(consulta.getIdConsulta(),
        consulta.getMedico().getIdMedico(),
        consulta.getPaciente().getIdPaciente());
  }

}
