package med.voll.api.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

  @Value("${jwt.secretKey}")
  private String secretKey;

  String issuer = "auth0-vol-med";

  public String generateToken(Usuario usuario) {
    try {
      Algorithm signatureAlgorithm = Algorithm.HMAC256(secretKey);
      return com.auth0.jwt.JWT.create()
          .withIssuer(issuer)
          .withSubject(usuario.getUsername())
          .withClaim("id", usuario.getId()) // Now you have access to usuario.getId()
          .withIssuedAt(new Date(System.currentTimeMillis()))
          .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))  // Expires in 1 week
          .sign(signatureAlgorithm);

    } catch (JWTCreationException exception) {
      // Invalid Signing configuration / Couldn't convert Claims.
      throw new RuntimeException(exception.getMessage());
    }
  }

  public boolean isTokenValid(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secretKey);
      com.auth0.jwt.JWT.require(algorithm)
          .withIssuer(issuer) // Also validates the issuer
          .build()
          .verify(token);
      return true; // Token is valid
    } catch (JWTVerificationException exception) {
      return false; // Token is invalid
    }
  }

  public String getUsernameFromToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secretKey);
      return com.auth0.jwt.JWT.require(algorithm)
          .withIssuer(issuer)
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException e) {
      return null; // Or handle the exception as needed
    }
  }

}
