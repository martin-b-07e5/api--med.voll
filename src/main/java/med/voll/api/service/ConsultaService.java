package med.voll.api.service;

import jakarta.transaction.Transactional;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaListadoDTO;
import med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

  @Autowired
  private ConsultaRepository consultaRepository;

  @Transactional
  public Page<ConsultaListadoDTO> listarPaginado(Pageable pageable) {
    // Fetch the page from the repository. findAll(pageable) returns a page of entities.
    Page<Consulta> consultasPage = consultaRepository.findAll(pageable);

    // Map the entity page to a DTOs page
    return consultasPage.map(ConsultaListadoDTO::new);
  }


}
