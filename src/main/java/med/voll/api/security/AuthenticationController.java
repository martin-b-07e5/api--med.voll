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
  private JwtCreateService jwtCreateService;

  @Autowired
  private AuthenticationManager authenticationManager;


  @PostMapping
  public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuarioDTO datosAutenticacionUsuario) {

    // Create an authentication token based on the user's credentials
    Authentication authToken = new UsernamePasswordAuthenticationToken(
        datosAutenticacionUsuario.username(),
        datosAutenticacionUsuario.password()
    );

    // Authenticate the user
    Authentication authUser = authenticationManager.authenticate(authToken);

    // Generate JWT token using JwtService
    String jwtToken = jwtCreateService.generateToken(authUser);

    // Return token in the response
//    return ResponseEntity.ok(new JwtResponseDTO(jwtToken));
    return ResponseEntity.ok(jwtToken);
  }

}
