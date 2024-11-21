package at.kaindorf.webshop.config;

import at.kaindorf.webshop.pojos.User;
import at.kaindorf.webshop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 07.11.2024
 * Time: 09:28
 */
@Service
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String rawPassword = authentication.getCredentials().toString();
    UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
    if (!passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
      log.info("BadCredentials: Invalid password");
      throw new BadCredentialsException("Invalid password");
    }
    return new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
