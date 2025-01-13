package med.voll.api.repository;

import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

  boolean existsByEmail(String email);

  boolean existsByDocumento(String documento);

  boolean existsById(Long id);

  List<Medico> findByInactivoTrue();

  Page<Medico> findByInactivoFalse(Pageable page);

  List<Medico> findByInactivo(boolean inactivo);

  @Query("SELECT m FROM Medico m WHERE m.id NOT IN (SELECT c.medico.id FROM Consulta c WHERE c.fecha = :fechaHora)")
  List<Medico> findMedicosDisponibles(@Param("fechaHora") LocalDateTime fechaHora);

}
