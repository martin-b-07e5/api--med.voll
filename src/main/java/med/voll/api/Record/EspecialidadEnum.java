package med.voll.api.Record;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EspecialidadEnum {
  ORTOPEDIA("ortopedia"),
  PEDIATRIA("pediatria"),
  CARDIOLOGIA("cardiologia"),
  GINECOLOGIA("ginecologia"),
  NEUROLOGIA("neurologia");

  private final String description;

  // constructor
  EspecialidadEnum(String description) {
    this.description = description;
  }

  /* @JsonValue:
       Instructs Jackson to use the descripcion field for serializing the enum.
       This means that when converting the enum to JSON, Jackson will use the value of description
        (e.g., "ortopedia"). */
  @JsonValue
  public String getDescription() {
    return description;
  }

  /* @JsonCreator:
       Defines a static method that Jackson will use to deserialize the value received in the JSON.
       It compares the received value (value) with the values of description, ignoring case sensitivity
       (equalsIgnoreCase). */
  @JsonCreator
  public static EspecialidadEnum fromValue(String value) {
    for (EspecialidadEnum especialidad : EspecialidadEnum.values()) {
      if (especialidad.description.equalsIgnoreCase(value)) {
        return especialidad;
      }
    }
    throw new IllegalArgumentException("Especialidad no válida: " + value);
  }

}
