package med.voll.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

  @Autowired
  private AuthenticationConfiguration authenticationConfiguration;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;


  @PostMapping
  public ResponseEntity<?> autenticarUsuario2(@RequestBody DatosAutenticacionUsuarioDTO datosAutenticacionUsuario) {
    // Crear un token de autenticación basado en las credenciales del usuario
    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
        datosAutenticacionUsuario.username(),
        datosAutenticacionUsuario.password()
    );

    // Autenticar al usuario
    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    // Generar el token JWT usando JwtService
    String jwt = jwtService.generateToken(authentication);

    // Devolver el token en la respuesta
    return ResponseEntity.ok(new JwtResponse(jwt));
  }


}
