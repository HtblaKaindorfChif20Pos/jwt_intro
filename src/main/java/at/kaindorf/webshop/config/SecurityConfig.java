package at.kaindorf.webshop.config;

import at.kaindorf.webshop.jwt.JwtAuthenticationFilter;
import at.kaindorf.webshop.jwt.JwtUnauthorizedEndpoint;
import at.kaindorf.webshop.pojos.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 07.11.2024
 * Time: 08:52
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private JwtUnauthorizedEndpoint jwtUnauthorizedEndpoint;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    log.info("Setup security filter chain");
    http.csrf(AbstractHttpConfigurer::disable)      // required when using JWT
        .authorizeHttpRequests(request ->
            request.requestMatchers("/api/auth/*").permitAll()   // * -> /api/auth/login    ** -> /api/auth/a/b/login
                .requestMatchers(HttpMethod.GET,"/api/resources/**").hasAnyAuthority(Role.USER.name())
                .requestMatchers(HttpMethod.GET,"/api/public/**").permitAll()
                .anyRequest().authenticated())
        .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtUnauthorizedEndpoint))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * create and return ProviderManager as AuthenticationManager
   *
   * @param http
   * @return
   */
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) {
    log.info("Create AuthenticationManager");
    return new ProviderManager(authenticationProvider());
  }

  /**
   * create nd retun custom AuthenticationProvider
   *
   * @return
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    log.info("Create AuthenticationProvider");
    return new UserAuthenticationProvider();
  }

  /**
   * create and return PasswordEncoder
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
