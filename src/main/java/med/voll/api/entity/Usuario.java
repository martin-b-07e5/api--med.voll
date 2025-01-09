package med.voll.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "usuario") /* default class name */
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

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


  //----------------------------------------------------------------
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return List.of();
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());

  }


  //----------------------------------------------------------------
  // agregado a mano
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }
//----------------------------------------------------------------

  @Override
  public boolean isAccountNonExpired() {
//    return UserDetails.super.isAccountNonExpired();
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
//    return UserDetails.super.isAccountNonLocked();
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
//    return UserDetails.super.isCredentialsNonExpired();
    return true;
  }

  @Override
  public boolean isEnabled() {
//    return UserDetails.super.isEnabled();
    return true;
  }

}
