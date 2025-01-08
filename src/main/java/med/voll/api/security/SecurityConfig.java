package med.voll.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/// https://docs.spring.io/spring-security/reference/servlet/getting-started.html#servlet-hello-auto-configuration

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private AutenticationService autenticationService;


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @ConditionalOnMissingBean(UserDetailsService.class)
  InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    // Generating a secure password using BCryptPasswordEncoder
    String generatedPassword = passwordEncoder().encode("admin");  // Encrypted password
    return new InMemoryUserDetailsManager(User.withUsername("admin")
        .password(generatedPassword)
        .roles("ADMIN")
        .build());
  }

  @Bean
  @ConditionalOnMissingBean(AuthenticationEventPublisher.class)
  DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) {
    return new DefaultAuthenticationEventPublisher(delegate);
  }

}
