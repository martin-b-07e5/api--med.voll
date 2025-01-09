package med.voll.api.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

  @Value("${jwt.secretKey}")
  private String secretKey;


  public String generateToken(Authentication authentication) {
    return Jwts.builder()
        .setSubject(authentication.getName())  // Nombre del usuario
        .setIssuedAt(new Date())               // Fecha de emisión
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expira en 1 hora
        .signWith(SignatureAlgorithm.HS256, secretKey)  // Firmar con la clave secreta
        .compact();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }


  public String extractUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
