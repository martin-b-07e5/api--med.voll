package med.voll.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

  @Autowired
  private AuthenticationConfiguration authenticationConfiguration;

  // login method
  @PostMapping
  public ResponseEntity autenticarUsuario(DatosAutenticacionUsuarioDTO datosAutenticacionUsuario) throws Exception {
    AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
    Authentication token = new UsernamePasswordAuthenticationToken(
        datosAutenticacionUsuario.username(),
        datosAutenticacionUsuario.password()
    );
    authenticationManager.authenticate(token);
    return ResponseEntity.ok().build();
  }


}
