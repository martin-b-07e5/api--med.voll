package med.voll.api.repository;

import med.voll.api.entity.Medico;
import med.voll.api.entity.MedicoListadoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
  boolean existsByEmail(String email);

  boolean existsByDocumento(String documento);

}
