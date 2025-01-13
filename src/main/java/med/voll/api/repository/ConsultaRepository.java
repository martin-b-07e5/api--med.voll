package med.voll.api.repository;

import med.voll.api.domain.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

  // Do not allow scheduling an appointment with a physician who already has another appointment scheduled.
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

  // Do not allow scheduling an appointment with a patient who already has another appointment scheduled in the same day.
  @Query("""
        SELECT COUNT(c) > 0
        FROM Consulta c
        WHERE c.paciente.id = :idPaciente
          AND FUNCTION('DATE', c.fecha) = FUNCTION('DATE', :fecha)
      """)
  boolean existsConsultaEnElMismoDia(
      @Param("idPaciente") Long idPaciente,
      @Param("fecha") LocalDateTime fecha
  );


}
