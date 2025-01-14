package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.ConsultaListadoDTO;
import med.voll.api.domain.consulta.ConsultaDatosDetalleDTO;
import med.voll.api.domain.consulta.ConsultaDatosReservaDTO;
import med.voll.api.service.ConsultaReservaService;
import med.voll.api.service.ConsultaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")  /* default path for this controller */
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

  @Autowired
  private ConsultaReservaService consultaReservaService;
  @Autowired
  private ConsultaService consultaService;

  private static final Logger logger = LoggerFactory.getLogger(ConsultaController.class);


  @PostMapping
  @Transactional
  public ResponseEntity<ConsultaDatosDetalleDTO> reservar(@RequestBody @Valid ConsultaDatosReservaDTO consultaDatosReservaDTO) {
    System.out.println("[ConsultaController]consultaDatosReservaDTO: " + consultaDatosReservaDTO);

    var consulta = consultaReservaService.reservar(consultaDatosReservaDTO);

    var consultaDetalleDTO = new ConsultaDatosDetalleDTO(
        consulta.getIdConsulta(),
        consulta.getMedico().getIdMedico(),
        consulta.getPaciente().getIdPaciente(),
        consulta.getFecha()
    );

    return ResponseEntity.ok(consultaDetalleDTO);
  }


  @GetMapping
  // http://localhost:8080/consultas?page=0&size=3&sort=idConsulta,desc
  public Page<ConsultaListadoDTO> listarPaginado(
      @PageableDefault(size = 3, sort = "idConsulta", direction = Sort.Direction.ASC) Pageable pageable) {

    // Print by console (log)
    logger.info("Página solicitada: {}", pageable.getPageNumber());
    logger.info("Tamaño de página: {}", pageable.getPageSize());
    logger.info("Ordenamiento: {}", pageable.getSort());

    return consultaService.listarPaginado(pageable);
  }

}
