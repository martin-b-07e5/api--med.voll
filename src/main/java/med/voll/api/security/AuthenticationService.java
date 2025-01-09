package med.voll.api.security;

import med.voll.api.entity.Usuario;
import med.voll.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  //  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    var usuario = usuarioRepository.findByUsername(username)
//        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//    return new org.springframework.security.core.userdetails.User(
//        usuario.getUsername(),
//        usuario.getPassword(),
//        usuario.getEnabled(),
//        true,
//        true,
//        true,
//        usuario.getRoles().stream()
//            .map(SimpleGrantedAuthority::new)
//            .collect(Collectors.toList())
//    );
//  }
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = (Usuario) usuarioRepository.findByUsername(username);

    if (usuario == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    return usuario;
  }


}
