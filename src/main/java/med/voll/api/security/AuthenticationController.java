package med.voll.api.security;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;


  @PostMapping
  public ResponseEntity<?> autenticarUsuario2(@RequestBody @Valid DatosAutenticacionUsuarioDTO datosAutenticacionUsuario) {

    // Create an authentication token based on the user's credentials
    Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
        datosAutenticacionUsuario.username(),
        datosAutenticacionUsuario.password()
    );

    // Authenticate the user
    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    // Generate JWT token using JwtService
    String jwt = jwtService.generateToken(authentication);

    // Return token in the response
    return ResponseEntity.ok(new JwtResponse(jwt));
  }

}
