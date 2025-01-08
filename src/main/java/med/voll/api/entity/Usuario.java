package med.voll.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "usuario") /* default class name */
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario") // name of the column in the DB
  private Long id;

  @NotBlank
  private String username;  // login

  @NotBlank
  private String password;

  private Boolean enabled;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> roles; // Example roles: ["ROLE_ADMIN", "ROLE_USER"]

}
