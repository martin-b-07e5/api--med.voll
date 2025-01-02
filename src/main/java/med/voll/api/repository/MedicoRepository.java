package med.voll.api.repository;

import med.voll.api.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
  boolean existsByEmail(String email);

  boolean existsByDocumento(String documento);
}
