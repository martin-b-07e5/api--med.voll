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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

// In the controller, simply delegate the task to the service.

@RestController
@RequestMapping("/medicos")
public class MedicoController {

  private static final Logger logger = LoggerFactory.getLogger(MedicoController.class);

  @Autowired
  private MedicoService medicoService;


  ///  read ----------------------------------------------------------------------

  @GetMapping("/listar")
  //  http://localhost:8080/medicos/listar
  public List<Medico> listarMedicos() {
    return medicoService.listarMedicos();
  }

  @GetMapping("/listarDTO")
  // http://localhost:8080/medicos/listarDTO
  public List<MedicoListadoDTO> listarDTO() {
    return medicoService.listarDTO();
  }

  @GetMapping
  // http://localhost:8080/medicos?page=0&size=2&sort=nombre,asc
  public Page<MedicoListadoDTO> listarPaginado(
      @PageableDefault(size = 2, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {

    // Print by console (log)
    logger.info("Página solicitada: {}", pageable.getPageNumber());
    logger.info("Tamaño de página: {}", pageable.getPageSize());
    logger.info("Ordenamiento: {}", pageable.getSort());

    return medicoService.listarPaginado(pageable);
  }

  @GetMapping("/equivalente")
  // http://localhost:8080/medicos/equivalente?page=0&size=2&sort=nombre,asc
  public Page<MedicoListadoDTO> listarPaginadoEquivalente(
      @PageableDefault(size = 2, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
    return medicoService.listarPaginadoEquivalente(pageable);
  }

  @GetMapping("/{id}")  // Endpoint to get a doctor's details by ID
  public ResponseEntity<MedicoDTO> getMedicoById(@PathVariable Long id) {
    Medico medico = medicoService.getMedicoById(id);  // Fetch the doctor from the service
    if (medico != null) {
      MedicoDTO medicoDTO = new MedicoDTO(medico);  // Map the entity to DTO
      return ResponseEntity.ok(medicoDTO);  // Return the doctor's details in the response
    } else {
      return ResponseEntity.notFound().build();  // Return 404 if the doctor is not found
    }
  }

  @GetMapping("/inactivos")
  //  http://localhost:8080/medicos/inactivos devuelve entidades
  public List<Medico> listarMedicosInactivosEntidades() {
    return medicoService.listarMedicosInactivosEntidades();
  }

  @GetMapping("/inactivosSimple")
  // http://localhost:8080/medicos/inactivosSimple  devuelve DTOs
  public List<MedicoListadoSimpleDTO> listarMedicosInactivosDTO() {
    return medicoService.listarMedicosInactivosDTO();
  }

  @GetMapping("/activosSimple")
  //  http://localhost:8080/medicos/activosSimple?page=0&size=3&sort=id,desc
  public Page<MedicoListadoSimpleDTO> listarMedicosActivosDTO(
      @PageableDefault(size = 2, sort = "id") Pageable page) {
    return medicoService.listarMedicosActivosDTO(page);
  }

  @GetMapping("/listarPorEstado")
  // http://localhost:8080/medicos/listarPorEstado?inactivo=true
  public List<MedicoListadoSimpleDTO> listarMedicosPorEstado(@RequestParam boolean inactivo) {
    return medicoService.listarMedicosPorEstado(inactivo);
  }


  ///  create ----------------------------------------------------------------------

  @PostMapping("/old")
  public void agregarMedico(@RequestBody @Valid MedicoDTO medicoDTO) {
    medicoService.addMedico(medicoDTO);
  }

  @PostMapping // Endpoint to add a new doctor
  public ResponseEntity<Void> addMedico(@RequestBody @Valid MedicoDTO medicoDTO) {
    // Adds the new doctor's details to the database and gets the created entity with the generated ID
    Medico medico = medicoService.addMedico(medicoDTO);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(medico.getId())  // Builds the URL with the generated ID
        .toUri();

    return ResponseEntity.created(location).build(); // Returns a 201 Created status with the location URL
  }


  ///  update ----------------------------------------------------------------------

  @PutMapping("/old")  // Endpoint to update a doctor's information
  public ResponseEntity actualizarMedicoSimple(@RequestBody @Valid MedicoUpdateDTO medicoUpdateDTO) {
    medicoService.updateMedico_getReferenceById(medicoUpdateDTO);  // Updates the doctor's details in the database
    return ResponseEntity.ok(medicoUpdateDTO);  // Returns the updated doctor data in the response
  }

  @PutMapping  // Endpoint to update a doctor's information
  public ResponseEntity actualizarMedico(@RequestBody @Valid MedicoUpdateDTO medicoUpdateDTO) {
    medicoService.updateMedico_getReferenceById(medicoUpdateDTO);  // Updates the doctor's details in the database
    Medico medicoActualizado = medicoService.getMedicoById(medicoUpdateDTO.id()); // Fetches the updated doctor from the database
    return ResponseEntity.ok(new MedicoUpdateDTO(medicoActualizado));  // Returns the updated doctor data in the response
  }

  @PatchMapping("/{id}/excluirPojo")
  // http://localhost:8080/medicos/39/excluirPojo
  public void excluirMedicoPojo(@PathVariable Long id) {
    medicoService.excluirMedicoPojo(id);
  }

  @PatchMapping("/{id}/excluirDTO")
  // http://localhost:8080/medicos/39/excluirDTO
  public void excluirDTO(@PathVariable Long id, @RequestBody MedicoExclusionDTO medicoExclusionDTO) {
    medicoService.excluirMedicoDTO(id, medicoExclusionDTO);
  }


  ///  delete ----------------------------------------------------------------------

  @DeleteMapping("/{id}")
  // http://localhost:8080/medicos/36
  public ResponseEntity eliminarMedicoHard(@PathVariable Long id) {
    medicoService.eliminarMedicoHard(id);
    return ResponseEntity.noContent().build();
  }


}
