//package med.voll.api.security;
//
//import med.voll.api.entity.Usuario;
//import med.voll.api.repository.UsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Set;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//  @Autowired
//  UsuarioRepository usuarioRepository;
//
//  @Autowired
//  PasswordEncoder passwordEncoder;
//
//  @Override
//  @Transactional
//  public void run(String... args) throws Exception {
//    if (usuarioRepository.count() == 0) {
//      Usuario admin = new Usuario();
//      admin.setUsername("admin");
//      admin.setPassword(passwordEncoder.encode("admin"));
//      admin.setEnabled(true);
//      admin.setRoles(Set.of("ROLE_ADMIN"));
//
//      usuarioRepository.save(admin);
//      System.out.println("Usuario admin creado con éxito.");
//    }
//
//
//  }
//}
