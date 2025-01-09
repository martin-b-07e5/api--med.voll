package med.voll.api.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {
  private String jwt;

  public JwtResponse(String jwt) {
    this.jwt = jwt;
  }

}
