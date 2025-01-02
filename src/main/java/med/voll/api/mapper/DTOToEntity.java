package med.voll.api.mapper;

import org.springframework.stereotype.Component;

@Component
public class DTOToEntity {

  public <T> T toEntity(Object dto, Class<T> entityClass) {
    try {
      return entityClass.getDeclaredConstructor(dto.getClass()).newInstance(dto);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error converting DTO to Entity: " + e.getMessage(), e);
    }
  }
}
