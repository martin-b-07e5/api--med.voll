package med.voll.api.mapper;

import med.voll.api.entity.DireccionDTO;
import med.voll.api.entity.EspecialidadEnum;
import med.voll.api.entity.Medico;
import med.voll.api.entity.MedicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class MedicoValidatorMapper {

  private final Validator validator;

  // Constructor-based dependency injection
  @Autowired
  public MedicoValidatorMapper() {
    // Configuración del validador de JSR-303 (Bean Validation)
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      this.validator = factory.getValidator();
    }
  }

  /// This Method validates the DTO and, if valid, converts it into a Medico entity.
  public Medico toEntity(MedicoDTO dto) {
    validar(dto); // Validation of the DTO before conversion
    return new Medico(dto); // We perform the conversion to the Medico entity
  }

  /// Validación personalizada para el DTO de Medico
  private void validar(MedicoDTO dto) {
    validateNombre(dto.nombre());
    validateEmail(dto.email());
    validateDocumento(dto.documento());
    validateEspecialidad(dto.especialidad());
    // validate Direction
    validateDireccion(dto.direccion());
  }

  private void validateNombre(String nombre) {
    if (nombre == null || nombre.isBlank()) {
      throw new IllegalArgumentException("El nombre no puede estar vacío");
    }
    String nombreRegex = "^[a-zA-ZÀ-ÿ\\s]+$";
    if (!nombre.matches(nombreRegex)) {
      throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
    }
  }

  private void validateEmail(String email) {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("El email no puede estar vacío");
    }
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    if (!email.matches(emailRegex)) {
      throw new IllegalArgumentException("El email no tiene un formato válido");
    }
  }

  private void validateDocumento(String documento) {
    if (documento == null || documento.isBlank()) {
      throw new IllegalArgumentException("El documento no puede estar vacío");
    }
    // Supongamos que se espera que el documento tenga un formato específico (ejemplo: número de identificación)
    String documentoRegex = "^\\d{4,10}$"; // Un ejemplo de formato numérico para el documento
    if (!documento.matches(documentoRegex)) {
      throw new IllegalArgumentException("El documento no tiene un formato válido");
    }
  }

  private void validateEspecialidad(EspecialidadEnum especialidad) {
    if (especialidad == null) {
      throw new IllegalArgumentException("La especialidad no puede ser null");
    }
  }


  /// Validación personalizada para la dirección
  private void validateDireccion(DireccionDTO direccionDTO) {
    validateCalle(direccionDTO.calle());
    validateNumero(direccionDTO.numero());
    validatePiso(direccionDTO.piso());
    validateCiudad(direccionDTO.ciudad());
    validateProvincia(direccionDTO.provincia());
    validatePais(direccionDTO.pais());
  }

  private void validateCalle(String calle) {
    if (calle == null || calle.isBlank()) {
      throw new IllegalArgumentException("La calle no puede estar vacía");
    }
    String calleRegex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s\\.]+$";


    if (!calle.matches(calleRegex)) {
      throw new IllegalArgumentException("La calle no tiene un formato válido");
    }
  }

  private void validateNumero(Integer numero) {
    if (numero == null) {
      throw new IllegalArgumentException("El número no puede ser null");
    }
    if (numero < 1 || numero > 9999) {
      throw new IllegalArgumentException("El número de la dirección debe estar entre 1 y 9999");
    }
  }

  private void validatePiso(String piso) {
    if (piso != null && !piso.matches("^[a-zA-Z0-9]+$")) {
      throw new IllegalArgumentException("El piso solo puede contener letras y números");
    }
  }

  private void validateCiudad(String ciudad) {
    if (ciudad == null || ciudad.isBlank()) {
      throw new IllegalArgumentException("La ciudad no puede estar vacía");
    }
    // Supongamos que se espera que la ciudad tenga un formato específico (ejemplo: nombre de la ciudad)
    String ciudadRegex = "^[a-zA-Z��-��\\s]+$";
    if (!ciudad.matches(ciudadRegex)) {
      throw new IllegalArgumentException("La ciudad no tiene un formato válido");
    }
  }

  private void validateProvincia(String provincia) {
    if (provincia == null || provincia.isBlank()) {
      throw new IllegalArgumentException("La provincia no puede estar vacía");
    }
    // Supongamos que se espera que la provincia tenga un formato específico (ejemplo: nombre de la provincia)
    String provinciaRegex = "^[a-zA-Z��-��\\s]+$";
    if (!provincia.matches(provinciaRegex)) {
      throw new IllegalArgumentException("La provincia no tiene un formato válido");
    }
  }

  private void validatePais(String pais) {
    if (pais == null || pais.isBlank()) {
      throw new IllegalArgumentException("El pais no puede estar vacío");
    }
    // Supongamos que se espera que el pais tenga un formato específico (ejemplo: nombre del país)
    String paisRegex = "^[a-zA-Z��-��\\s]+$";
    if (!pais.matches(paisRegex)) {
      throw new IllegalArgumentException("El pais no tiene un formato válido");
    }
  }

}
