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
        .setSubject(authentication.getName())  // User's name
        .setIssuedAt(new Date())               // Date of issue (emisión)
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Expires in 1 week
        .signWith(SignatureAlgorithm.HS256, secretKey)  // Sign with the secret key
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
