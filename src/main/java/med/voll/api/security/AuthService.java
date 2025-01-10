package med.voll.api.security;

import med.voll.api.entity.Usuario;
import med.voll.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
//    Usuario usuario = (Usuario) usuarioRepository.findByUsername(username);

    if (usuario.isEmpty()) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    return usuario.orElse(null);
  }

}
