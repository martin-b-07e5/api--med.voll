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
  private AuthenticationManager authenticationManager;


//  @PostMapping
//  public ResponseEntity<?> autenticarUsuario(@RequestBody DatosAutenticacionUsuarioDTO datosAutenticacionUsuario) throws Exception {
//    Authentication token = new UsernamePasswordAuthenticationToken(
//        datosAutenticacionUsuario.username(),
//        datosAutenticacionUsuario.password()
//    );
//    authenticationManager.authenticate(token);  // Esto debería autenticar al usuario
//    return ResponseEntity.ok().build();
//  }

  @PostMapping
  public ResponseEntity<?> autenticarUsuario(@RequestBody DatosAutenticacionUsuarioDTO datosAutenticacionUsuario) throws Exception {
    Authentication token = new UsernamePasswordAuthenticationToken(
        datosAutenticacionUsuario.username(),
        datosAutenticacionUsuario.password()
    );
    Authentication authentication = authenticationManager.authenticate(token);
//    String jwt = jwtService.generateToken(authentication);  // Aquí deberías generar el JWT
//    return ResponseEntity.ok(new JwtResponse(jwt));  // Devuelves el token al cliente
    return null;  // temporal
  }


}
