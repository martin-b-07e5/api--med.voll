package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.ConsultaDatosDetalleDTO;
import med.voll.api.domain.consulta.ConsultaDatosReservaDTO;
import med.voll.api.service.ConsultaReservaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")  /* default path for this controller */
public class ConsultaController {

  @Autowired
  private ConsultaReservaService consultaReservaService;

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

//  @GetMapping
//  // http://localhost:8080/consultas?page=0&size=3&sort=nombre,asc
//  public Page<ConsultaDatosReservaDTO> listarPaginado(
//      @PageableDefault(size = 3, sort = "idMedico", direction = Sort.Direction.ASC) Pageable pageable) {
//
//    // Print by console (log)
//    logger.info("Página solicitada: {}", pageable.getPageNumber());
//    logger.info("Tamaño de página: {}", pageable.getPageSize());
//    logger.info("Ordenamiento: {}", pageable.getSort());
//
//    return consultaService.listarPaginado(pageable);
//  }

}
