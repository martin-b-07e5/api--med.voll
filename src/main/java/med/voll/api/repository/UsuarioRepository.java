package med.voll.api.repository;

import med.voll.api.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  Optional<Usuario> findByUsername(String username);
//  UserDetails findByUsername(String username);

}
