package med.voll.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private AuthenticationService authenticationService;


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


  // https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#disable-csrf
  //  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        // ...
//        .csrf((csrf) -> csrf.disable());
////        .authorizeHttpRequests(authorize -> authorize
////            .requestMatchers("/user/**").hasRole("USER")
////                .requestMatchers("/admin/**").hasRole("ADMIN")
////                .anyRequest().authenticated()
////        );
////        .formLogin(withDefaults());
//    return http.build();
//  }

  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    //        .authorizeHttpRequests(req -> {
//          req.requestMatchers(HttpMethod.POST, "/login")
//          .permitAll();
//          req.anyRequest()
//          .authenticated();
//        })
//        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
    return http.build();
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }


}
