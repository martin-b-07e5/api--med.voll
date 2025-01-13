package med.voll.api.repository;

import med.voll.api.domain.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

  @Query("""
          SELECT COUNT(c) > 0
          FROM Consulta c
          WHERE c.medico.id = :idMedico
            AND (
                 (:fechaInicio BETWEEN c.fecha AND c.fecha + 1 HOUR)
              OR (:fechaFin BETWEEN c.fecha AND c.fecha + 1 HOUR)
              OR (c.fecha BETWEEN :fechaInicio AND :fechaFin)
            )
      """)
  boolean existsConsultaConConflicto(
      @Param("idMedico") Long idMedico,
      @Param("fechaInicio") LocalDateTime fechaInicio,
      @Param("fechaFin") LocalDateTime fechaFin
  );


}
