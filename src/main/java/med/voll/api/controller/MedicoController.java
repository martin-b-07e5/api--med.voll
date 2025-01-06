package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.entity.*;
import med.voll.api.service.MedicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// In the controller, simply delegate the task to the service.

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  private static final Logger logger = LoggerFactory.getLogger(MedicoController.class);

  @Autowired
  private MedicoService medicoService;


  // read
  //  http://localhost:8080/medicos/listar
  @GetMapping("/listar")
  public List<Medico> listarMedicos() {
    return medicoService.listarMedicos();
  }

  // http://localhost:8080/medicos/listarDTO
  @GetMapping("/listarDTO")
  public List<MedicoListadoDTO> listarDTO() {
    return medicoService.listarDTO();
  }


  // http://localhost:8080/medicos
  // http://localhost:8080/medicos?page=0&size=2&sort=nombre,asc
  @GetMapping
  public Page<MedicoListadoDTO> listarPaginado(
      @PageableDefault(size = 2, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {

    // Print by console (log)
    logger.info("Página solicitada: {}", pageable.getPageNumber());
    logger.info("Tamaño de página: {}", pageable.getPageSize());
    logger.info("Ordenamiento: {}", pageable.getSort());

    return medicoService.listarPaginado(pageable);
  }

  // http://localhost:8080/medicos/equivalente
  // http://localhost:8080/medicos/equivalente?page=0&size=2&sort=nombre,asc
  @GetMapping("/equivalente")
  public Page<MedicoListadoDTO> listarPaginadoEquivalente(
      @PageableDefault(size = 2, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
    return medicoService.listarPaginadoEquivalente(pageable);
  }


  // create
  @PostMapping
  public void agregarMedico(@RequestBody @Valid MedicoDTO medicoDTO) {
    medicoService.registrarMedico(medicoDTO);
  }

  // update
  @PutMapping
  public void actualizarMedico(@RequestBody @Valid MedicoUpdateDTO medicoUpdateDTO) {
    medicoService.updateMedico_getReferenceById(medicoUpdateDTO);
  }

  // delete en DB
  // http://localhost:8080/medicos/36
  @DeleteMapping("/{id}")
  public void eliminarMedicoHard(@PathVariable Long id) {
    medicoService.eliminarMedicoHard(id);
  }

  // excluir (delete lógico)
  // http://localhost:8080/medicos/39/excluirPojo
  @PatchMapping("/{id}/excluirPojo")
  public void excluirMedicoPojo(@PathVariable Long id) {
    medicoService.excluirMedicoPojo(id);
  }

  // http://localhost:8080/medicos/39/excluirDTO
  @PatchMapping("/{id}/excluirDTO")
  public void excluirDTO(@PathVariable Long id, @RequestBody MedicoExclusionDTO medicoExclusionDTO) {
    medicoService.excluirMedicoDTO(id, medicoExclusionDTO);
  }


  // devuelve entidades
  //  http://localhost:8080/medicos/inactivos
  @GetMapping("/inactivos")
  public List<Medico> listarMedicosInactivosEntidades() {
    return medicoService.listarMedicosInactivosEntidades();
  }

  // devuelve DTOs
  //  http://localhost:8080/medicos/inactivosSimple
  @GetMapping("/inactivosSimple")
  public List<MedicoListadoSimpleDTO> listarMedicosInctivosDTO() {
    return medicoService.listarMedicosInctivosDTO();
  }


  // http://localhost:8080/medicos/listarPorEstado?inactivo=true
  // http://localhost:8080/medicos/listarPorEstado?inactivo=false
  @GetMapping("/listarPorEstado")
  public List<MedicoListadoSimpleDTO> listarMedicosPorEstado(@RequestParam boolean inactivo) {
    return medicoService.listarMedicosPorEstado(inactivo);
  }


}
