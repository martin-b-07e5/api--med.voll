package med.voll.api.security;

import med.voll.api.entity.Usuario;
import med.voll.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

  @Autowired
  UsuarioRepository usuarioRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    if (usuarioRepository.count() == 1) {
      Usuario usuario = new Usuario();
      usuario.setUsername("usuario");
      usuario.setPassword(passwordEncoder.encode("q1w2e3"));
      usuario.setEnabled(true);
      usuario.setRoles(Set.of("ROLE_USER"));

      usuarioRepository.save(usuario);
      System.out.println("Usuario creado con éxito.");
    }


  }
}
