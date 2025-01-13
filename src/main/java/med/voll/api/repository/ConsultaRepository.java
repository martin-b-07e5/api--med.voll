package med.voll.api.repository;

import med.voll.api.domain.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

  boolean existsByMedico_IdMedicoAndFechaBetween(Long idMedico, LocalDateTime inicio, LocalDateTime fin);

}
