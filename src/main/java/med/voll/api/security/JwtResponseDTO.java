package med.voll.api.security;

//@Setter
//@Getter
//public class JwtResponse {
//  private String jwt;
//
//  public JwtResponse(String jwt) {
//    this.jwt = jwt;
//  }
//
//}
public record JwtResponseDTO(String jwt) {
}